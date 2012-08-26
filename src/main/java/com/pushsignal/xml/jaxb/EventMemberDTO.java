package com.pushsignal.xml.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.EventMember;

@XmlRootElement(name="eventMember")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventMemberDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long eventMemberId;
	
	private UserDTO user;

	public void setEventMemberId(long eventMemberId) {
		this.eventMemberId = eventMemberId;
	}

	public long getEventMemberId() {
		return eventMemberId;
	}

	public EventMemberDTO() {
	}

	public EventMemberDTO(final EventMember eventMember) {
		this.eventMemberId = eventMember.getEventMemberId();
		this.user = new UserDTO(eventMember.getUser());
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}
}
