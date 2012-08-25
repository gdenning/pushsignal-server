package com.pushsignal.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.logic.TriggerAlertLogic;
import com.pushsignal.xml.jaxb.TriggerAlertDTO;

@Scope("singleton")
@Controller
public class TriggerAlertController extends AbstractController {
	@Autowired
	private TriggerAlertLogic triggerAlertLogic;

	/**
	 * Get details about a particular trigger alert.
	 * 
	 * @param triggerAlertId
	 * @return XML model of the trigger alert.
	 */
	@RequestMapping(value="/alerts/{alertId}")
	public ModelAndView getTriggerAlert(final HttpServletResponse response,
			@PathVariable final long alertId) {
		try {
			final TriggerAlert triggerAlert = triggerAlertLogic.getTriggerAlert(getAuthenticatedEmail(), alertId);
			return getXMLModelAndView("triggerAlert", new TriggerAlertDTO(triggerAlert));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
