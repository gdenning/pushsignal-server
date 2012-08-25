package com.pushsignal.xml.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.Activity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long activityId;

	private String description;
	
	private long createdDateInMilliseconds;
	
	private long points;

	public ActivityDTO() {
	}

	public ActivityDTO(final Activity activity) {
		this.activityId = activity.getActivityId();
		this.description = activity.getDescription();
		this.points = activity.getPoints();
		this.createdDateInMilliseconds = activity.getCreateDate().getTime();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getActivityId() {
		return activityId;
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
