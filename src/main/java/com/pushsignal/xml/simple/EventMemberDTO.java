package com.pushsignal.xml.simple;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="eventMember", strict=false)
public class EventMemberDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private long eventMemberId;
	
	@Element
	private UserDTO user;

	public void setEventMemberId(long eventMemberId) {
		this.eventMemberId = eventMemberId;
	}

	public long getEventMemberId() {
		return eventMemberId;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return user.getName();
	}
}
