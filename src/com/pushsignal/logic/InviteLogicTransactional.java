package com.pushsignal.logic;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.dao.EventInviteDAO;
import com.pushsignal.dao.EventMemberDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventInvite;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;

@Scope("singleton")
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//NOTE: This class should only be called by the InviteLogic class.
class InviteLogicTransactional extends AbstractLogic {
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private EventInviteDAO eventInviteDAO;

	@Autowired
	private EventMemberDAO eventMemberDAO;

	public EventInvite createInvite(final User userMe,
			final long eventId, final String email) {
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: " + eventId);
		}
		if (!event.isMember(userMe)) {
			throw new BadRequestException("Unable to invite user to event because you are not a member");
		}
		EventInvite invite = new EventInvite();
		invite.setCreateDate(new Date());
		final User user = userDAO.findUserByEmail(email);
		if (user == null) {
			if (event.isInvited(email)) {
				throw new BadRequestException("Email address has already been invited to event: "
						+ event.getName());
			}
			invite.setEmail(email);
		} else {
			if (event.isInvited(user)) {
				throw new BadRequestException("User has already been invited to event: " + event.getName());
			}
			if (event.isMember(user)) {
				throw new BadRequestException("User is already a member of event: " + event.getName());
			}
			invite.setUser(user);
		}
		invite.setEvent(event);
		final EventInvite updatedInvite = eventInviteDAO.store(invite);

		return updatedInvite;
	}

	public EventMember acceptInvite(final User userMe, final long inviteId) {
		final EventInvite invite = eventInviteDAO.findEventInviteByPrimaryKey(inviteId);
		if (invite == null) {
			throw new ResourceNotFoundException("Unable to locate invite with passed ID: "
					+ inviteId);
		}
		if ((invite.getUser() == null) || !invite.getUser().equals(userMe)) {
			throw new BadRequestException("This invite was not intended for you");
		}
		if (invite.getEvent().isMember(userMe)) {
			throw new BadRequestException("You are already a member of this event");
		}
		EventMember eventMember = new EventMember();
		eventMember.setEvent(invite.getEvent());
		eventMember.setUser(invite.getUser());
		eventMember = eventMemberDAO.store(eventMember);

		eventInviteDAO.remove(invite);

		return eventMember;
	}

	public EventInvite declineInvite(final User userMe, final long inviteId) {
		final EventInvite invite = eventInviteDAO.findEventInviteByPrimaryKey(inviteId);
		if (invite == null) {
			throw new ResourceNotFoundException("Unable to locate invite with passed ID: "
					+ inviteId);
		}
		if ((invite.getUser() == null) || !invite.getUser().equals(userMe)) {
			throw new BadRequestException("This invite was not intended for you");
		}
		eventInviteDAO.remove(invite);

		return invite;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void setEventInviteDAO(EventInviteDAO eventInviteDAO) {
		this.eventInviteDAO = eventInviteDAO;
	}

	public void setEventMemberDAO(EventMemberDAO eventMemberDAO) {
		this.eventMemberDAO = eventMemberDAO;
	}
}
