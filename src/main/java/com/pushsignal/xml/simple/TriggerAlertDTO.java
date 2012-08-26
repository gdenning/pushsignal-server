package com.pushsignal.xml.simple;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="triggerAlert", strict=false)
public class TriggerAlertDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private long triggerAlertId;

	@Element
	private long triggerId;

	@Element
	private UserDTO user;
	
	@Element
	private long modifiedDateInMilliseconds;
	
	@Element
	private String status;
	
	public void setTriggerAlertId(long triggerAlertId) {
		this.triggerAlertId = triggerAlertId;
	}

	public long getTriggerAlertId() {
		return this.triggerAlertId;
	}

	public void setTriggerId(long triggerId) {
		this.triggerId = triggerId;
	}

	public long getTriggerId() {
		return triggerId;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	public long getModifiedDateInMilliseconds() {
		return modifiedDateInMilliseconds;
	}

	public void setStatus(String status) {
		this.status = status.toString();
	}

	public String getStatus() {
		return this.status;
	}
}
