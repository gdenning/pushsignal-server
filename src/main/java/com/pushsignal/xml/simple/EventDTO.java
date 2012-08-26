package com.pushsignal.xml.simple;

import java.io.Serializable;
import java.util.LinkedHashSet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="event", strict=false)
public class EventDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private long eventId;

	@Element
	private String name;

	@Element(required=false)
	private String description;

	@Element
	private long createdDateInMilliseconds;

	@Element
	private String triggerPermission;
	
	@Element
	private boolean publicFlag;

	@Element(name="createdBy")
	private UserDTO owner;

	@ElementList(name="member", inline=true, required=false)
	private LinkedHashSet<EventMemberDTO> members;
	
	@Element(required=false)
	private Long lastTriggeredDateInMilliseconds;
	
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

	public void setMembers(LinkedHashSet<EventMemberDTO> members) {
		this.members = members;
	}

	public LinkedHashSet<EventMemberDTO> getMembers() {
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

	public boolean isMember(UserDTO user) {
		boolean userFound = false;
		for (EventMemberDTO eventMember : getMembers()) {
			if (eventMember.getUser().equals(user)) {
				userFound = true;
			}
		}
		return userFound;
	}
}
