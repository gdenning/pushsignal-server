package com.pushsignal.xml.simple;

import java.util.LinkedHashSet;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.pushsignal.xml.simple.EventDTO;

@Root(name="eventSet", strict=false)
public class EventSetDTO {
	@ElementList(required=false, inline=true)
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
