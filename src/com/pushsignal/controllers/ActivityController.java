package com.pushsignal.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pushsignal.domain.Activity;
import com.pushsignal.logic.ActivityLogic;
import com.pushsignal.xml.jaxb.ActivityDTO;
import com.pushsignal.xml.jaxb.ActivitySetDTO;

@Scope("singleton")
@Controller
public class ActivityController extends AbstractController {
	@Autowired
	private ActivityLogic activityLogic;

	/**
	 * Get a list of all activities for the logged-in user.
	 * 
	 * @return XML model of a set of activities.
	 */
	@RequestMapping(value="/activities/all")
	public ModelAndView getAllActivities(final HttpServletResponse response) {
		try {
			final Set<Activity> activities = activityLogic.getAllActivities(getAuthenticatedEmail());
			final ActivitySetDTO activityDTOs = new ActivitySetDTO();
			for (Activity activity : activities) {
				activityDTOs.getActivities().add(new ActivityDTO(activity));
			}
			return getXMLModelAndView("activities", activityDTOs);
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}

	/**
	 * Get details about a particular activity.
	 * 
	 * @param activityId
	 * @return XML model of the event.
	 */
	@RequestMapping(value="/activities/{activityId}")
	public ModelAndView getEvent(final HttpServletResponse response,
			@PathVariable final long activityId) {
		try {
			final Activity activity = activityLogic.getActivity(getAuthenticatedEmail(), activityId);
			return getXMLModelAndView("activity", new ActivityDTO(activity));
		} catch (final Exception ex) {
			return getErrorModelAndView(response, ex);
		}
	}
}
