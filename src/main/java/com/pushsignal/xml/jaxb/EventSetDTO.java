package com.pushsignal.xml.jaxb;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="eventSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventSetDTO {
	@XmlElement(name="event")
	private LinkedHashSet<EventDTO> events;

	public void setEvents(LinkedHashSet<EventDTO> events) {
		this.events = events;
	}

	public LinkedHashSet<EventDTO> getEvents() {
		if (events == null) {
			events = new LinkedHashSet<EventDTO>();
		}
		return events;
	}
}
