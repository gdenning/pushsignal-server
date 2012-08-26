package com.pushsignal.xml.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="activity", strict=false)
public class ActivityDTO {
	private static final long serialVersionUID = 1L;
	
	@Element
	private long activityId;

	@Element(required=false)
	private String description;

	@Element
	private long createdDateInMilliseconds;

	@Element
	private long points;

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCreatedDateInMilliseconds(long createdDateInMilliseconds) {
		this.createdDateInMilliseconds = createdDateInMilliseconds;
	}

	public long getCreatedDateInMilliseconds() {
		return createdDateInMilliseconds;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public long getPoints() {
		return points;
	}
}