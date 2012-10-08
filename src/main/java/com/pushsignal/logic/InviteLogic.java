package com.pushsignal.logic;

import static com.pushsignal.push.Notifier.staticId;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.domain.EventInvite;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.User;
import com.pushsignal.email.EmailClient;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;
import com.pushsignal.push.Notifier;

@Scope("singleton")
@Service
public class InviteLogic extends AbstractLogic {
	private static final Logger LOG = Logger.getLogger(InviteLogic.class);

	private static final int POINTS_FOR_INVITE_ACCEPTED = 10;

	private static final String ACCEPTABLE_EMAIL_REGEX = "(?i)^[A-Z0-9._%-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}$";

	@Autowired
	private InviteLogicTransactional persist;

	@Autowired
	private EventInviteDAO eventInviteDAO;

	@Autowired
	private ActivityLogic activityLogic;

	@Autowired
	private Notifier notifier;

	@Autowired
	private EmailClient emailClient;

	public Set<EventInvite> getAllInvites(final String authenticatedEmail) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		return userMe.getEventInvites();
	}

	public EventInvite getInvite(final String authenticatedEmail, final long inviteId) {
		final EventInvite invite = eventInviteDAO.findEventInviteByPrimaryKey(inviteId);
		if (invite == null) {
			throw new ResourceNotFoundException("Unable to locate invite with passed ID: "
							+ inviteId);
		}
		return invite;
	}

	public EventInvite createInvite(final String authenticatedEmail,
			final long eventId, final String email) {
		LOG.debug("InviteLogic.createInvite(" + authenticatedEmail + "," + eventId + "," + email + ")");

		if (!email.matches(ACCEPTABLE_EMAIL_REGEX)) {
			throw new BadRequestException("Email address " + email + " is invalid.");
		}
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final EventInvite updatedInvite = persist.createInvite(userMe, eventId, email);
		final User user = updatedInvite.getUser();

		// Notify the recipient that they've been invited
		if (user != null) {
			notifier.sendNotifications(user, "INVITE:", staticId(updatedInvite.getEventInviteId()));
		}

		StringBuffer body = new StringBuffer();
		body.append(userMe.getName()).append(" (").append(userMe.getEmail()).append(") has invited you to the event ").append(updatedInvite.getEvent().getName()).append(".\n\n");
		if (user == null) {
			body.append("To accept the invite, download PushSignal to your Android device from https://market.android.com/details?id=com.pushsignal, and register for a free account using this email address.\n");
		} else {
			body.append("To accept the invite, open PushSignal on your Android device and click the \"Invites\" tab.\n");
			body.append("If no longer have PushSignal installed, you can download it from https://market.android.com/details?id=com.pushsignal.\n");
		}
		emailClient.sendEmail(email, userMe.getEmail() + " has invited you to a PushSignal event", body.toString());

		return updatedInvite;
	}

	public EventMember acceptInvite(final String authenticatedEmail, final long inviteId) {
		LOG.debug("InviteLogic.acceptInvite(" + authenticatedEmail + "," + inviteId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final EventMember eventMember = persist.acceptInvite(userMe, inviteId);

		// Notify all event members that the event has changed
		notifier.sendNotifications(eventMember.getEvent(), "EVENT_CHANGED:", staticId(eventMember.getEvent().getEventId()));

		final EventInvite invite = eventInviteDAO.findEventInviteByPrimaryKey(inviteId);
		if (invite != null && invite.getCreatedBy() != null) {
			activityLogic.createActivity(invite.getCreatedBy(), "Invite to event " + invite.getEvent().getName() + " was accepted by " + userMe.getName(), POINTS_FOR_INVITE_ACCEPTED);
		}

		return eventMember;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public EventInvite declineInvite(final String authenticatedEmail, final long inviteId) {
		LOG.debug("InviteLogic.declineInvite(" + authenticatedEmail + "," + inviteId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final EventInvite invite = persist.declineInvite(userMe, inviteId);

		return invite;
	}

	public void setPersist(final InviteLogicTransactional persist) {
		this.persist = persist;
	}

	public void setNotifier(final Notifier notifier) {
		this.notifier = notifier;
	}

	public void setEventInviteDAO(final EventInviteDAO eventInviteDAO) {
		this.eventInviteDAO = eventInviteDAO;
	}

	public void setEmailClient(final EmailClient emailClient) {
		this.emailClient = emailClient;
	}
}
