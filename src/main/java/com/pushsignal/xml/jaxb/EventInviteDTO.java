package com.pushsignal.xml.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.EventInvite;

@XmlRootElement(name="eventInvite")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventInviteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long eventInviteId;

	private long createdDateInMilliseconds;

	private EventDTO event;

	private String email;

	private UserDTO user;

	public EventInviteDTO() {
	}

	public EventInviteDTO(final EventInvite eventInvite) {
		this.eventInviteId = eventInvite.getEventInviteId();
		this.event = new EventDTO(eventInvite.getEvent());
		this.email = eventInvite.getEmail();
		this.createdDateInMilliseconds = eventInvite.getCreateDate().getTime();
		if (eventInvite.getUser() != null) {
			this.user = new UserDTO(eventInvite.getUser());
		}
	}
	
	public void setEventInviteId(long eventInviteId) {
		this.eventInviteId = eventInviteId;
	}

	public long getEventInviteId() {
		return this.eventInviteId;
	}

	public void setCreatedDateInMilliseconds(long createdDateInMilliseconds) {
		this.createdDateInMilliseconds = createdDateInMilliseconds;
	}

	public long getCreatedDateInMilliseconds() {
		return createdDateInMilliseconds;
	}

	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

}
