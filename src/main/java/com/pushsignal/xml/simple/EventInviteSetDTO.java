package com.pushsignal.xml.simple;

import java.util.LinkedHashSet;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="eventInviteSet", strict=false)
public class EventInviteSetDTO {
	@ElementList(required=false, inline=true)
	private LinkedHashSet<EventInviteDTO> eventInvites;
	
	public void setEventInvites(LinkedHashSet<EventInviteDTO> eventInvites) {
		this.eventInvites = eventInvites;
	}

	public LinkedHashSet<EventInviteDTO> getEventInvites() {
		if (eventInvites == null) {
			eventInvites = new LinkedHashSet<EventInviteDTO>();
		}
		return eventInvites;
	}
}
