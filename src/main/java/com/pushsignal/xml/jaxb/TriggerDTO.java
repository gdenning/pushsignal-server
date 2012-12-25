package com.pushsignal.xml.jaxb;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.Trigger;
import com.pushsignal.domain.TriggerAlert;

@XmlRootElement(name="trigger")
@XmlAccessorType(XmlAccessType.FIELD)
public class TriggerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long triggerId;

	private long createdDateInMilliseconds;

	private EventDTO event;

	private UserDTO user;

	@XmlElement(name="triggerAlert")
	private Set<TriggerAlertDTO> triggerAlerts;

	public TriggerDTO() {
	}

	public TriggerDTO(final Trigger trigger) {
		this.triggerId = trigger.getTriggerId();
		this.createdDateInMilliseconds = trigger.getCreateDate().getTime();
		this.event = new EventDTO(trigger.getEvent());
		if (trigger.getUser() != null) {
			this.user = new UserDTO(trigger.getUser());
		}
		this.triggerAlerts = new LinkedHashSet<TriggerAlertDTO>();
		for (TriggerAlert triggerAlert : trigger.getTriggerAlerts()) {
			this.triggerAlerts.add(new TriggerAlertDTO(triggerAlert));
		}
	}

	public void setTriggerId(long triggerId) {
		this.triggerId = triggerId;
	}

	public long getTriggerId() {
		return this.triggerId;
	}

	public void setCreatedDateInMilliseconds(long createdDateInMilliseconds) {
		this.createdDateInMilliseconds = createdDateInMilliseconds;
	}

	public long getCreatedDateInMilliseconds() {
		return createdDateInMilliseconds;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}

	public EventDTO getEvent() {
		return event;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setTriggerAlerts(Set<TriggerAlertDTO> triggerAlerts) {
		this.triggerAlerts = triggerAlerts;
	}

	public Set<TriggerAlertDTO> getTriggerAlerts() {
		if (triggerAlerts == null) {
			triggerAlerts = new LinkedHashSet<TriggerAlertDTO>();
		}
		return triggerAlerts;
	}
}
