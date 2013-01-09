package com.pushsignal.logic;

import static com.pushsignal.push.Notifier.perTriggerId;
import static com.pushsignal.push.Notifier.staticId;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pushsignal.dao.TriggerDAO;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;
import com.pushsignal.push.Notifier;


@Scope("singleton")
@Service
public class TriggerLogic extends AbstractLogic {

	private static final Logger LOG = LoggerFactory.getLogger(TriggerLogic.class);

	private static final int POINTS_FOR_TRIGGER_ACKNOWLEDGED = 5;

	private static final String TRIGGER_ALERT = "TRIGGER_ALERT:";

	private static final String EVENT_CHANGED = "EVENT_CHANGED:";

	private static final String TRIGGER_ACK = "TRIGGER_ACK:";

	@Autowired
	private TriggerLogicTransactional persist;

	@Autowired
	private TriggerDAO triggerDAO;

	@Autowired
	private ActivityLogic activityLogic;

	@Autowired
	private Notifier notifier;

	public Trigger getTrigger(final String authenticatedEmail, final long triggerId) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		Trigger trigger = triggerDAO.findTriggerByPrimaryKey(triggerId);
		if (trigger == null) {
			throw new ResourceNotFoundException("Unable to locate trigger with passed ID: "
					+ triggerId);
		}
		if (!trigger.isMember(userMe)) {
			throw new BadRequestException("Unable to retrieve a trigger you are not a member of");
		}
		return trigger;
	}

	public Set<Trigger> getMissedTriggers(final String authenticatedEmail) {
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		return triggerDAO.findActiveTriggersForUser(userMe);
	}

	public Trigger createTrigger(final String authenticatedEmail, final long eventId) {
		LOG.debug("TriggerLogic.createTrigger(" + authenticatedEmail + "," + eventId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		// Persist and flush trigger and trigger alerts first so that clients can find
		// them in the database.
		final Trigger trigger = persist.createTrigger(userMe, eventId);

		notifier.sendNotifications(trigger, TRIGGER_ALERT, perTriggerId(), userMe);
		notifier.sendNotifications(trigger, EVENT_CHANGED, staticId(eventId));

		return trigger;
	}


	public Trigger createTriggerByGuid(final String urlGuid) {
		LOG.debug("TriggerLogic.createTriggerByGuid(" + urlGuid + ")");

		// Persist and flush trigger and trigger alerts first so that clients can find
		// them in the database.
		final Trigger trigger = persist.createTriggerByGuid(urlGuid);

		// Notify all event members about the trigger and that the event changed (the last trigger date)
		notifier.sendNotifications(trigger, TRIGGER_ALERT, perTriggerId());
		notifier.sendNotifications(trigger, EVENT_CHANGED, staticId(trigger.getEvent().getEventId()));

		return trigger;
	}

	public TriggerAlert ackTrigger(final String authenticatedEmail, final long triggerId) {
		LOG.debug("TriggerLogic.ackTrigger(" + authenticatedEmail + "," + triggerId + ")");

		final Trigger trigger = triggerDAO.findTriggerByPrimaryKey(triggerId);
		if (trigger == null) {
			throw new ResourceNotFoundException("Unable to locate trigger with passed ID: "
					+ triggerId);
		}

		// Persist and flush trigger alert first so that clients will see changes to it
		// in the database.
		final User userMe = getAuthenticatedUser(authenticatedEmail);
		final TriggerAlert myTriggerAlert = trigger.getTriggerAlertForUser(userMe);
		persist.ackTrigger(myTriggerAlert.getTriggerAlertId());

		// Notify all subscribers that you have acknowledged
		notifier.sendNotifications(trigger, TRIGGER_ACK, staticId(myTriggerAlert.getTriggerAlertId()));

		// Give points to the user who sent the trigger
		if (trigger.getUser() != null) {
			activityLogic.createActivity(trigger.getUser(), "Trigger of event " + trigger.getEvent().getName() + " was acknowledged by " + userMe.getName(), POINTS_FOR_TRIGGER_ACKNOWLEDGED);
		}

		return myTriggerAlert;
	}

	public TriggerAlert silenceTrigger(final String authenticatedEmail, final long triggerId) {
		LOG.debug("TriggerLogic.silenceTrigger(" + authenticatedEmail + "," + triggerId + ")");

		final User userMe = getAuthenticatedUser(authenticatedEmail);

		// Persist and flush trigger alert first so that clients will see changes to it
		// in the database.
		final TriggerAlert myTriggerAlert = persist.silenceTrigger(userMe, triggerId);

		// Notify all of my devices that this trigger is now silent
		notifier.sendNotifications(myTriggerAlert.getUser(), "TRIGGER_SILENCE:", staticId(myTriggerAlert.getTriggerAlertId()));

		return myTriggerAlert;
	}

	public void setNotifier(final Notifier notifier) {
		this.notifier = notifier;
	}

	public void setTriggerDAO(final TriggerDAO triggerDAO) {
		this.triggerDAO = triggerDAO;
	}

	public void setPersist(final TriggerLogicTransactional persist) {
		this.persist = persist;
	}

	public void setActivityLogic(final ActivityLogic activityLogic) {
		this.activityLogic = activityLogic;
	}
}
