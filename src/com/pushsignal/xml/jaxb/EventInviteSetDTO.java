package com.pushsignal.xml.jaxb;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="eventInviteSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventInviteSetDTO {
	@XmlElement(name="eventInvite")
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
