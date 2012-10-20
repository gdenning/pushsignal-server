package com.pushsignal.logic;

import static com.pushsignal.push.Notifier.staticId;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.ActivityDAO;
import com.pushsignal.domain.Activity;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;
import com.pushsignal.push.Notifier;

@Scope("singleton")
@Service
public class ActivityLogic extends AbstractLogic {
	private static final Logger LOG = LoggerFactory.getLogger(ActivityLogic.class);

	private static final int MAX_ACTIVITIES = 25;

	@Autowired
	private ActivityDAO activityDAO;

	@Autowired
	private Notifier notifier;

	public Set<Activity> getAllActivities(final String authenticatedEmail) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		return activityDAO.findActivitiesByUserID(userMe.getUserId(), MAX_ACTIVITIES);
	}

	public Activity getActivity(final String authenticatedEmail, final long activityId) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final Activity activity = activityDAO.findActivityByPrimaryKey(activityId);
		if (activity == null) {
			throw new ResourceNotFoundException("Unable to locate activity with passed ID: "
					+ activityId);
		}
		if (!activity.getUser().equals(userMe)) {
			throw new BadRequestException("Unable to retrieve an activity that does not belong to you");
		}
		return activity;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Activity createActivity(
			final User user,
			final String description,
			final int points) {
		LOG.debug("ActivityLogic.createActivity(" + user + "," + description + "," + points + ")");

		Activity activity = new Activity();
		activity.setCreateDate(new Date());
		activity.setDescription(description);
		activity.setPoints(points);
		activity.setUser(user);

		final Activity updatedActivity = activityDAO.store(activity);

		if (points != 0) {
			notifier.sendNotifications(user, "POINTS_CHANGED:", staticId(userDAO.getPoints(user.getUserId())));
		}
		notifier.sendNotifications(user, "ACTIVITY_CREATED:", staticId(updatedActivity.getActivityId()));

		return updatedActivity;
	}
}
