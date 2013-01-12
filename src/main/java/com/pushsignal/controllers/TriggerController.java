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

import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.logic.TriggerLogic;
import com.pushsignal.xml.jaxb.TriggerAlertDTO;
import com.pushsignal.xml.jaxb.TriggerDTO;
import com.pushsignal.xml.jaxb.TriggerSetDTO;

@Scope("singleton")
@Controller
public class TriggerController extends AbstractController {
	@Autowired
	private TriggerLogic triggerLogic;

	/**
	 * Get details about a particular trigger.
	 * 
	 * @param triggerId
	 * @return XML model of the trigger.
	 */
	@RequestMapping(value="/triggers/{triggerId}")
	public ModelAndView getTrigger(final HttpServletResponse response,
			@PathVariable final long triggerId) {
		try {
			final Trigger trigger = triggerLogic.getTrigger(getAuthenticatedEmail(), triggerId);
			return getXMLModelAndView("trigger", new TriggerDTO(trigger));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Get a set of trigger details that are still active for me.
	 * 
	 * @return XML model of a set of triggers.
	 */
	@RequestMapping(value="/triggers/missed")
	public ModelAndView getMissedTriggers(final HttpServletResponse response) {
		try {
			final Set<Trigger> triggers = triggerLogic.getMissedTriggers(getAuthenticatedEmail());
			final TriggerSetDTO triggerDTOs = new TriggerSetDTO();
			for (Trigger trigger : triggers) {
				triggerDTOs.getTriggers().add(new TriggerDTO(trigger));
			}
			return getXMLModelAndView("triggers", triggerDTOs);
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Create (fire) a trigger for an event.
	 * 
	 * @param eventId
	 * @return XML model of the trigger.
	 */
	@RequestMapping(value="/triggers/create", method = RequestMethod.POST)
	public ModelAndView createTrigger(final HttpServletResponse response,
			@RequestParam final long eventId, @RequestParam(required = false) final String message) {
		try {
			final Trigger trigger = triggerLogic.createTrigger(getAuthenticatedEmail(), eventId, message);
			return getXMLModelAndView("trigger", new TriggerDTO(trigger));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Create (fire) a trigger for an event.
	 * 
	 * @param eventGuid the urlGuid for the event
	 * @return XML model of the trigger.
	 */
	@RequestMapping(value="/triggers/create/{eventGuid}", method = RequestMethod.GET)
	public ModelAndView createTrigger(final HttpServletResponse response,
			@PathVariable final String eventGuid, @RequestParam(required = false) final String message) {
		try {
			final Trigger trigger = triggerLogic.createTriggerByGuid(eventGuid, message);
			return getXMLModelAndView("trigger", new TriggerDTO(trigger));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Acknowledge the trigger.
	 * 
	 * @param triggerId
	 * @return XML model of the triggerAlert
	 */
	@RequestMapping(value="/triggers/{triggerId}/ack", method = RequestMethod.POST)
	public ModelAndView ackTrigger(final HttpServletResponse response,
			@PathVariable final long triggerId) {
		try {
			final TriggerAlert triggerAlert = triggerLogic.ackTrigger(getAuthenticatedEmail(), triggerId);
			return getXMLModelAndView("triggerAlert", new TriggerAlertDTO(triggerAlert));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Silence (ignore) the trigger.
	 * 
	 * @param triggerId
	 * @return XML model of the triggerAlert.
	 */
	@RequestMapping(value="/triggers/{triggerId}/silence", method = RequestMethod.POST)
	public ModelAndView silenceTrigger(final HttpServletResponse response,
			@PathVariable final long triggerId) {
		try {
			final TriggerAlert triggerAlert = triggerLogic.silenceTrigger(getAuthenticatedEmail(), triggerId);
			return getXMLModelAndView("triggerAlert", new TriggerAlertDTO(triggerAlert));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
