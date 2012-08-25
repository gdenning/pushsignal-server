package com.pushsignal.xml.jaxb;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="activitySet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivitySetDTO {
	@XmlElement(name="activity")
	private LinkedHashSet<ActivityDTO> activities;

	public void setActivities(LinkedHashSet<ActivityDTO> activities) {
		this.activities = activities;
	}

	public LinkedHashSet<ActivityDTO> getActivities() {
		if (activities == null) {
			activities = new LinkedHashSet<ActivityDTO>();
		}
		return activities;
	}
}
