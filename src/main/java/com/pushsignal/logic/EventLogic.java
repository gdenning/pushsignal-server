package com.pushsignal.logic;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.domain.User;
import com.pushsignal.email.EmailClient;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;
import com.pushsignal.push.Notifier;

import static com.pushsignal.push.Notifier.staticId;

@Scope("singleton")
@Service
public class EventLogic extends AbstractLogic {
	private static final Logger LOG = LoggerFactory.getLogger(EventLogic.class);

	private static final int MAX_PUBLIC_EVENTS = 25;
	private static final int POINTS_FOR_JOINING_PUBLIC_EVENT = 10;
	private static final int POINTS_FOR_CREATING_EVENT = 25;

	@Autowired
	private EventLogicTransactional persist;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ActivityLogic activityLogic;

	@Autowired
	private Notifier notifier;

	@Autowired
	private EmailClient emailClient;

	public Set<Event> getAllEvents(final String authenticatedEmail) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		return eventDAO.findEventsByUserId(userMe.getUserId());
	}

	public Set<Event> getPublicEvents() {
		return eventDAO.findPublicEvents(MAX_PUBLIC_EVENTS);
	}

	public Event getEvent(final String authenticatedEmail, final long eventId) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (event.isPublicFlag() == false) {
			final EventMember eventMember = event.getEventMemberForUser(userMe);
			if (eventMember == null) {
				throw new BadRequestException("Unable to retrieve an event you are not a member of");
			}
		}
		return event;
	}

	public Event createEvent(
			final String authenticatedEmail,
			final String name,
			final String description,
			final TriggerPermissionEnum triggerPermission,
			final boolean publicFlag) {
		LOG.debug("EventLogic.createEvent(" + authenticatedEmail + "," + name + "," + description + "," + triggerPermission.name() + "," + publicFlag + ")");

		if (name.length() == 0) {
			throw new BadRequestException("Event name is required");
		}
		final User userMe = getAuthenticatedUser(authenticatedEmail);

		final Event event = persist.createEvent(userMe, name, description, triggerPermission, publicFlag);

		if (triggerPermission == TriggerPermissionEnum.URL_ONLY) {
			StringBuffer body = new StringBuffer();
			body.append("You have created URL triggered event '").append(name).append("'.\n\n");
			body.append("To trigger this event, click this link: http://pushsignal.com/api/triggers/create/").append(event.getUrlGuid()).append("\n\n");
			body.append("If you enjoy PushSignal, please add a review at https://market.android.com/details?id=com.pushsignal.\n");
			emailClient.sendEmail(authenticatedEmail, "PushSignal event '" + name + "' has been created", body.toString());
		}

		activityLogic.createActivity(userMe, "Created event " + event.getName(), POINTS_FOR_CREATING_EVENT);

		return event;
	}

	public Event updateEvent(
			final String authenticatedEmail,
			final long eventId,
			final String name,
			final String description,
			final TriggerPermissionEnum triggerPermission,
			final boolean publicFlag) {
		LOG.debug("EventLogic.updateEvent(" + authenticatedEmail + "," + eventId + "," + name + "," + description + "," + triggerPermission.name() + "," + publicFlag + ")");

		if (name.length() == 0) {
			throw new BadRequestException("Event name is required");
		}
		final User userMe = getAuthenticatedUser(authenticatedEmail);

		final Event event = persist.updateEvent(userMe, eventId, name, description,
				triggerPermission, publicFlag);

		notifier.sendNotifications(event, "EVENT_CHANGED:", staticId(event.getEventId()));

		return event;
	}

	public EventMember joinEvent(final String authenticatedEmail, final long eventId) {
		LOG.debug("EventLogic.joinEvent(" + authenticatedEmail + "," + eventId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		final Event event = persist.joinEvent(userMe, eventId);

		notifier.sendNotifications(event, "EVENT_CHANGED:", staticId(event.getEventId()));

		activityLogic.createActivity(userMe, "Joined public event " + event.getName(), POINTS_FOR_JOINING_PUBLIC_EVENT);

		return event.getEventMemberForUser(userMe);
	}

	public void leaveEvent(final String authenticatedEmail, final long eventId) {
		LOG.debug("EventLogic.leaveEvent(" + authenticatedEmail + "," + eventId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		final Event event = persist.leaveEvent(userMe, eventId);

		if (event.isPublicFlag()) {
			notifier.sendNotifications(event, "EVENT_CHANGED:", staticId(event.getEventId()));
		} else {
			notifier.sendNotifications(event, "EVENT_CHANGED:", staticId(event.getEventId()), userMe);
			notifier.sendNotifications(userMe, "EVENT_DELETED:", staticId(eventId));
		}
	}

	public void deleteEvent(final String authenticatedEmail, final long eventId) {
		LOG.debug("EventLogic.deleteEvent(" + authenticatedEmail + "," + eventId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		final Event event = persist.deleteEvent(userMe, eventId);

		notifier.sendNotifications(event, "EVENT_DELETED:", staticId(event.getEventId()));
	}

	public void setEventDAO(final EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void setActivityLogic(final ActivityLogic activityLogic) {
		this.activityLogic = activityLogic;
	}

	public void setPersist(final EventLogicTransactional persist) {
		this.persist = persist;
	}

	public void setNotifier(final Notifier notifier) {
		this.notifier = notifier;
	}
}
