package com.pushsignal.logic;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.EventDAO;
import com.pushsignal.dao.TriggerAlertDAO;
import com.pushsignal.dao.TriggerDAO;
import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;
import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;
import com.pushsignal.domain.TriggerAlert.TriggerAlertStatusEnum;
import com.pushsignal.domain.TriggerPermissionEnum;
import com.pushsignal.domain.User;
import com.pushsignal.exceptions.BadRequestException;
import com.pushsignal.exceptions.ResourceNotFoundException;

@Scope("singleton")
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
// NOTE: This class should only be called by the TriggerLogic class.
class TriggerLogicTransactional {
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private TriggerDAO triggerDAO;

	@Autowired
	private TriggerAlertDAO triggerAlertDAO;

	public Trigger createTrigger(final User userMe, final long eventId, final String message) {
		final Event event = eventDAO.findEventByPrimaryKey(eventId);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed ID: "
					+ eventId);
		}
		if (event.getTriggerPermission().equals(TriggerPermissionEnum.URL_ONLY)) {
			throw new BadRequestException("This event only allows URL triggering");
		}
		if (event.getTriggerPermission().equals(TriggerPermissionEnum.ALL_MEMBERS) && !event.isMember(userMe)) {
			throw new BadRequestException("This event only allows event member triggering");
		}
		if (event.getTriggerPermission().equals(TriggerPermissionEnum.OWNER_ONLY) && !userMe.equals(event.getOwner())) {
			throw new BadRequestException("This event only allows owner triggering");
		}
		
		Date now = new Date();
		
		event.setLastTriggerDate(now);
		eventDAO.store(event);

		Trigger trigger = new Trigger();
		trigger.setCreateDate(now);
		trigger.setEvent(event);
		trigger.setUser(userMe);
		trigger.setMessage(message);

		final Set<TriggerAlert> triggerAlerts = new LinkedHashSet<TriggerAlert>();
		for (final EventMember eventMember : event.getMembers()) {
			final TriggerAlert triggerAlert = new TriggerAlert();
			triggerAlert.setModifiedDate(trigger.getCreateDate());
			triggerAlert.setTrigger(trigger);
			triggerAlert.setUser(eventMember.getUser());
			if (eventMember.getUser().equals(userMe)) {
				triggerAlert.setStatus(TriggerAlertStatusEnum.ACKNOWLEDGED);
			} else {
				triggerAlert.setStatus(TriggerAlertStatusEnum.ACTIVE);
			}
			triggerAlerts.add(triggerAlert);
		}
		trigger.setTriggerAlerts(triggerAlerts);
		trigger = triggerDAO.store(trigger);

		return trigger;
	}

	public Trigger createTriggerByGuid(final String urlGuid, final String message) {
		final Event event = eventDAO.findEventByGuid(urlGuid);
		if (event == null) {
			throw new ResourceNotFoundException("Unable to locate event with passed Guid: "
					+ urlGuid);
		}
		if (!event.getTriggerPermission().equals(TriggerPermissionEnum.URL_ONLY)) {
			throw new BadRequestException("This event does not allow public URL triggering");
		}

		Trigger trigger = new Trigger();
		trigger.setCreateDate(new Date());
		trigger.setEvent(event);
		trigger.setUser(null);
		trigger.setMessage(message);

		final Set<TriggerAlert> triggerAlerts = new LinkedHashSet<TriggerAlert>();
		for (final EventMember eventMember : event.getMembers()) {
			final TriggerAlert triggerAlert = new TriggerAlert();
			triggerAlert.setModifiedDate(trigger.getCreateDate());
			triggerAlert.setTrigger(trigger);
			triggerAlert.setUser(eventMember.getUser());
			triggerAlert.setStatus(TriggerAlertStatusEnum.ACTIVE);
			triggerAlerts.add(triggerAlert);
		}
		trigger.setTriggerAlerts(triggerAlerts);
		trigger = triggerDAO.store(trigger);

		return trigger;
	}

	public void ackTrigger(final long triggerAlertId) {
		TriggerAlert triggerAlert = triggerAlertDAO.findTriggerAlertByPrimaryKey(triggerAlertId);
		if (triggerAlert == null) {
			throw new BadRequestException("You cannot acknowledge a trigger that was not sent to you.");
		}
		if (triggerAlert.getStatus() == TriggerAlertStatusEnum.ACKNOWLEDGED) {
			throw new BadRequestException("You have already acknowledged this trigger");
		}

		triggerAlert.setModifiedDate(new Date());
		triggerAlert.setStatus(TriggerAlertStatusEnum.ACKNOWLEDGED);
		triggerAlert = triggerAlertDAO.store(triggerAlert);
	}

	public TriggerAlert silenceTrigger(final User userMe, final long triggerId) {
		final Trigger trigger = triggerDAO.findTriggerByPrimaryKey(triggerId);
		if (trigger == null) {
			throw new ResourceNotFoundException("Unable to locate trigger with passed ID: "
					+ triggerId);
		}
		TriggerAlert triggerAlert = trigger.getTriggerAlertForUser(userMe);
		if (triggerAlert == null) {
			throw new BadRequestException("You cannot acknowledge a trigger that was not sent to you.");
		}
		if ((triggerAlert.getStatus() == TriggerAlertStatusEnum.SILENCED)
				|| (triggerAlert.getStatus() == TriggerAlertStatusEnum.ACKNOWLEDGED)) {
			throw new BadRequestException("You have already silenced or acknowledged this trigger");
		}

		triggerAlert.setModifiedDate(new Date());
		triggerAlert.setStatus(TriggerAlertStatusEnum.SILENCED);
		triggerAlert = triggerAlertDAO.store(triggerAlert);

		return triggerAlert;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void setTriggerDAO(TriggerDAO triggerDAO) {
		this.triggerDAO = triggerDAO;
	}

	public void setTriggerAlertDAO(TriggerAlertDAO triggerAlertDAO) {
		this.triggerAlertDAO = triggerAlertDAO;
	}
}
