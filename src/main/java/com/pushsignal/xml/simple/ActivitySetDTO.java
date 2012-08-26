package com.pushsignal.xml.simple;

import java.util.LinkedHashSet;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="activitySet", strict=false)
public class ActivitySetDTO {
	@ElementList(required=false, inline=true)
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
