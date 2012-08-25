package com.pushsignal.xml.jaxb;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.pushsignal.domain.Event;
import com.pushsignal.domain.EventMember;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long eventId;

	private String name;

	private String description;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date createDate;
	
	private long createdDateInMilliseconds;
	
	private String triggerPermission;
	
	private boolean publicFlag;

	@XmlElement(name="createdBy")
	private UserDTO owner;

	@XmlElement(name="eventMember")
	private Set<EventMemberDTO> members;
	
	private Long lastTriggeredDateInMilliseconds;
	
	public EventDTO() {
	}

	public EventDTO(final Event event) {
		this.eventId = event.getEventId();
		this.name = event.getName();
		this.description = event.getDescription();
		this.createDate = event.getCreateDate();
		this.createdDateInMilliseconds = event.getCreateDate().getTime();
		if (event.getLastTriggerDate() != null) {
			this.lastTriggeredDateInMilliseconds = event.getLastTriggerDate().getTime();
		}
		this.setTriggerPermission(event.getTriggerPermission().name());
		this.setPublicFlag(event.isPublicFlag());
		this.setOwner(new UserDTO(event.getOwner()));
		this.members = new LinkedHashSet<EventMemberDTO>();
		for (EventMember eventMember : event.getMembers()) {
			this.members.add(new EventMemberDTO(eventMember));
		}
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public long getEventId() {
		return this.eventId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreatedDateInMilliseconds(long createdDateInMilliseconds) {
		this.createdDateInMilliseconds = createdDateInMilliseconds;
	}

	public long getCreatedDateInMilliseconds() {
		return createdDateInMilliseconds;
	}

	public void setTriggerPermission(String triggerPermission) {
		this.triggerPermission = triggerPermission;
	}

	public String getTriggerPermission() {
		return triggerPermission;
	}

	public void setPublicFlag(boolean publicFlag) {
		this.publicFlag = publicFlag;
	}

	public boolean isPublicFlag() {
		return publicFlag;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setMembers(Set<EventMemberDTO> members) {
		this.members = members;
	}

	public Set<EventMemberDTO> getMembers() {
		if (members == null) {
			members = new LinkedHashSet<EventMemberDTO>();
		}
		return members;
	}

	public void setLastTriggeredDateInMilliseconds(
			Long lastTriggeredDateInMilliseconds) {
		this.lastTriggeredDateInMilliseconds = lastTriggeredDateInMilliseconds;
	}

	public Long getLastTriggeredDateInMilliseconds() {
		return lastTriggeredDateInMilliseconds;
	}
}
