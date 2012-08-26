package com.pushsignal.xml.jaxb;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="triggerSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class TriggerSetDTO {
	@XmlElement(name="trigger")
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
