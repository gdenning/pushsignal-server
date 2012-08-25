package com.pushsignal.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.logic.EventLogic;
import com.pushsignal.xml.jaxb.DeleteResultDTO;
import com.pushsignal.xml.jaxb.EventDTO;
import com.pushsignal.xml.jaxb.EventMemberDTO;
import com.pushsignal.xml.jaxb.EventSetDTO;

@Scope("singleton")
@Controller
public class EventController extends AbstractController {
	@Autowired
	private EventLogic eventLogic;

	/**
	 * Get a list of all events that the logged-in user is subscribed to.
	 * 
	 * @return XML model of a set of events.
	 */
	@RequestMapping(value="/events/all")
	public ModelAndView getAllEvents(final HttpServletResponse response) {
		try {
			final Set<Event> events = eventLogic.getAllEvents(getAuthenticatedEmail());
			final EventSetDTO eventDTOs = new EventSetDTO();
			for (Event event : events) {
				eventDTOs.getEvents().add(new EventDTO(event));
			}
			return getXMLModelAndView("events", eventDTOs);
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Get a list of all public events.
	 * 
	 * @return XML model of a set of events.
	 */
	@RequestMapping(value="/events/public")
	public ModelAndView getPublicEvents(final HttpServletResponse response) {
		try {
			final Set<Event> events = eventLogic.getPublicEvents();
			final EventSetDTO eventDTOs = new EventSetDTO();
			for (Event event : events) {
				eventDTOs.getEvents().add(new EventDTO(event));
			}
			return getXMLModelAndView("events", eventDTOs);
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Get details about a particular event.
	 * 
	 * @param eventId
	 * @return XML model of the event.
	 */
	@RequestMapping(value="/events/{eventId}")
	public ModelAndView getEvent(final HttpServletResponse response,
			@PathVariable final long eventId) {
		try {
			final Event event = eventLogic.getEvent(getAuthenticatedEmail(), eventId);
			return getXMLModelAndView("event", new EventDTO(event));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Create a new event.
	 * 
	 * @param name
	 * @param description
	 * @param triggerPermission
	 * @param publicFlag
	 * @return XML model of event.
	 */
	@RequestMapping(value="/events/create", method = RequestMethod.POST)
	public ModelAndView createEvent(final HttpServletResponse response, 
			@RequestParam final String name,
			@RequestParam final String description,
			@RequestParam final String triggerPermission,
			@RequestParam final boolean publicFlag) {
		try {
			final Event event = eventLogic.createEvent(getAuthenticatedEmail(), name, description, 
					TriggerPermissionEnum.valueOf(triggerPermission), publicFlag);
			return getXMLModelAndView("event", new EventDTO(event));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Update the name and/or description of an event.
	 * 
	 * @param eventId
	 * @param name
	 * @param description
	 * @return XML model of the event.
	 */
	@RequestMapping(value="/events/{eventId}", method = RequestMethod.POST)
	public ModelAndView updateEvent(final HttpServletResponse response,
			@PathVariable final long eventId,
			@RequestParam final String name,
			@RequestParam final String description,
			@RequestParam final String triggerPermission,
			@RequestParam final boolean publicFlag) {
		try {
			final Event event = eventLogic.updateEvent(getAuthenticatedEmail(), eventId,
					name, description,
					TriggerPermissionEnum.valueOf(triggerPermission), publicFlag);
			return getXMLModelAndView("event", new EventDTO(event));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Join a public event.
	 * 
	 * @param eventId
	 * @return XML model of the EventMember object.
	 */
	@RequestMapping(value="/events/{eventId}/join", method = RequestMethod.POST)
	public ModelAndView joinEvent(final HttpServletResponse response,
			@PathVariable final long eventId) {
		try {
			final EventMember eventMember = eventLogic.joinEvent(getAuthenticatedEmail(), eventId);
			return getXMLModelAndView("eventMember", new EventMemberDTO(eventMember));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Unsubscribe from an event.
	 * 
	 * @param eventId
	 * @return XML model of the DeleteResult object.
	 */
	@RequestMapping(value="/events/{eventId}/leave", method = RequestMethod.POST)
	public ModelAndView leaveEvent(final HttpServletResponse response,
			@PathVariable final long eventId) {
		try {
			eventLogic.leaveEvent(getAuthenticatedEmail(), eventId);
			return getXMLModelAndView("eventMember", new DeleteResultDTO("eventMember"));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Delete an event that you created.
	 * 
	 * @param eventId
	 * @return XML model of the DeleteResult object.
	 */
	@RequestMapping(value="/events/{eventId}", method = RequestMethod.DELETE)
	public ModelAndView deleteEvent(final HttpServletResponse response,
			@PathVariable final long eventId) {
		try {
			eventLogic.deleteEvent(getAuthenticatedEmail(), eventId);
			return getXMLModelAndView("event", new DeleteResultDTO("event"));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
