package com.pushsignal.xml.simple;

import java.util.LinkedHashSet;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="triggerSet", strict=false)
public class TriggerSetDTO {
	@ElementList(required=false, inline=true)
	private LinkedHashSet<TriggerDTO> triggers;

	public void setTriggers(LinkedHashSet<TriggerDTO> triggers) {
		this.triggers = triggers;
	}

	public LinkedHashSet<TriggerDTO> getTriggers() {
		if (triggers == null) {
			triggers = new LinkedHashSet<TriggerDTO>();
		}
		return triggers;
	}
}
