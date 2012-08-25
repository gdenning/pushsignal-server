package com.pushsignal.logic;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;

@Scope("singleton")
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//NOTE: This class should only be called by the EventLogic class.
class EventLogicTransactional {
	@Autowired
	private EventDAO eventDAO;

	public Event createEvent(final User userMe, final String name, final String description,
			final TriggerPermissionEnum triggerPermission,
			final boolean publicFlag) {
		Event event = new Event();
		event.setCreateDate(new Date());
		event.setName(name);
		event.setDescription(description);
		event.setOwner(userMe);
		event.setTriggerPermission(triggerPermission);
		event.setPublicFlag(publicFlag);
		event.setUrlGuid(UUID.randomUUID().toString());

		final EventMember eventMember = new EventMember();
		eventMember.setEvent(event);
		eventMember.setUser(userMe);
		event.getMembers().add(eventMember);

		event = eventDAO.store(event);
		return event;
	}

	public Event updateEvent(final User userMe, final long eventId, final String name,
			final String description, final TriggerPermissionEnum triggerPermission,
			final boolean publicFlag) {
		Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (!event.getOwner().equals(userMe)) {
			throw new BadRequestException("Only the event owner is allowed to update an event");
		}

		event.setName(name);
		event.setDescription(description);
		event.setTriggerPermission(triggerPermission);
		event.setPublicFlag(publicFlag);
		event = eventDAO.store(event);

		return event;
	}

	public Event joinEvent(final User userMe, final long eventId) {
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (!event.isPublicFlag()) {
			throw new BadRequestException("Unable to join a private event without an invite.");
		}
		if (event.isMember(userMe)) {
			throw new BadRequestException("You are already a member of this event.");
		}

		final EventMember eventMember = new EventMember();
		eventMember.setEvent(event);
		eventMember.setUser(userMe);
		event.getMembers().add(eventMember);

		return eventDAO.store(event);
	}

	public Event leaveEvent(final User userMe, final long eventId) {
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (event.getOwner().equals(userMe)) {
			throw new BadRequestException("Unable to leave an event that you created");
		}
		final EventMember eventMember = event.getEventMemberForUser(userMe);
		if (eventMember == null) {
			throw new BadRequestException("Unable to leave an event you are not a member of");
		}

		event.getMembers().remove(eventMember);
		return eventDAO.store(event);
	}

	public Event deleteEvent(final User userMe, final long eventId) {
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (!event.getOwner().equals(userMe)) {
			throw new BadRequestException("Only the event owner is allowed to delete an event");
		}

		eventDAO.remove(event);

		return event;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
}
