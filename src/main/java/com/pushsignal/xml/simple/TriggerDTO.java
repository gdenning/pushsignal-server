package com.pushsignal.xml.simple;

import java.io.Serializable;
import java.util.LinkedHashSet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="trigger", strict=false)
public class TriggerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private long triggerId;

	@Element
	private long createdDateInMilliseconds;
	
	@Element
	private EventDTO event;

	@Element(required = false)
	private UserDTO user;
	
	@Element(required = false)
	private String message;

	@ElementList(name="triggerAlert", inline=true, required=false)
	private LinkedHashSet<TriggerAlertDTO> triggerAlerts;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTriggerAlerts(LinkedHashSet<TriggerAlertDTO> triggerAlerts) {
		this.triggerAlerts = triggerAlerts;
	}

	public LinkedHashSet<TriggerAlertDTO> getTriggerAlerts() {
		return triggerAlerts;
	}
}
