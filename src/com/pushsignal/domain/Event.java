package com.pushsignal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@NamedQueries({
		@NamedQuery(name = "findPublicEvents", query = "select myEvent from Event myEvent join myEvent.members myMember where publicFlag = 1 group by myEvent order by count(myMember) desc, myEvent.name"),
		@NamedQuery(name = "findEventByGuid", query = "select myEvent from Event myEvent where UrlGuid = ?1"),
		@NamedQuery(name = "findEventsByUserId", query = "select myEvent from Event myEvent join myEvent.members myMember join myMember.user myUser where myUser.userId = ?1 order by myEvent.name"),
		@NamedQuery(name = "findEventsByNameContaining", query = "select myEvent from Event myEvent where myEvent.name like ?1 order by myEvent.name")
})
@Table(catalog = "PushSignal", name = "TEvent")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

    @Column(name = "EventID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eventId;

	@Column(name = "Name", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String name;

	@Column(name = "Description", length = 1000, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastTriggerDate", nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private Date lastTriggerDate;
	
	@Column(name = "UrlGuid", length = 50, nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private String urlGuid;
	
	@Column(name = "TriggerPermission", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private TriggerPermissionEnum triggerPermission;

	@Column(name = "PublicFlag", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private boolean publicFlag;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "CreateUserID", referencedColumnName = "UserID", nullable = false) })
	@ForeignKey(name = "FK_Event_User")
	private User owner;

	@OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@ForeignKey(name = "FK_EventMember_Event")
	private Set<EventMember> members;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@ForeignKey(name = "FK_EventInvite_Event")
	private Set<EventInvite> invites;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@ForeignKey(name = "FK_Trigger_Event")
	private Set<Trigger> triggers;

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

	public void setLastTriggerDate(Date lastTriggerDate) {
		this.lastTriggerDate = lastTriggerDate;
	}

	public Date getLastTriggerDate() {
		return lastTriggerDate;
	}

	public void setUrlGuid(String urlGuid) {
		this.urlGuid = urlGuid;
	}

	public String getUrlGuid() {
		return urlGuid;
	}

	public void setTriggerPermission(TriggerPermissionEnum triggerPermission) {
		this.triggerPermission = triggerPermission;
	}

	public TriggerPermissionEnum getTriggerPermission() {
		return triggerPermission;
	}

	public void setPublicFlag(boolean publicFlag) {
		this.publicFlag = publicFlag;
	}

	public boolean isPublicFlag() {
		return publicFlag;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

	public void setMembers(Set<EventMember> members) {
		this.members = members;
	}

	public Set<EventMember> getMembers() {
		if (members == null) {
			members = new LinkedHashSet<EventMember>();
		}
		return members;
	}

	public void setInvites(Set<EventInvite> invites) {
		this.invites = invites;
	}

	public Set<EventInvite> getInvites() {
		if (invites == null) {
			invites = new LinkedHashSet<EventInvite>();
		}
		return invites;
	}

	public void setTriggers(Set<Trigger> triggers) {
		this.triggers = triggers;
	}

	public Set<Trigger> getTriggers() {
		if (triggers == null) {
			triggers = new LinkedHashSet<Trigger>();
		}
		return triggers;
	}

	public boolean isMember(User user) {
		boolean userFound = false;
		for (EventMember eventMember : getMembers()) {
			if (eventMember.getUser().equals(user)) {
				userFound = true;
			}
		}
		return userFound;
	}

	public boolean isInvited(User user) {
		boolean userFound = false;
		for (EventInvite eventInvite : getInvites()) {
			if (eventInvite.getUser() != null && eventInvite.getUser().equals(user)) {
				userFound = true;
			}
		}
		return userFound;
	}

	public boolean isInvited(String email) {
		boolean emailFound = false;
		for (EventInvite eventInvite : getInvites()) {
			if (eventInvite.getEmail() != null && eventInvite.getEmail().equals(email)) {
				emailFound = true;
			}
		}
		return emailFound;
	}

	public EventMember getEventMemberForUser(final User user) {
		for (EventMember eventMember : getMembers()) {
			if (eventMember.getUser().equals(user)) {
				return eventMember;
			}
		}
		return null;
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("eventId=[").append(eventId).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("description=[").append(description).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + (publicFlag ? 1231 : 1237);
		result = prime
				* result
				+ ((triggerPermission == null) ? 0 : triggerPermission
						.hashCode());
		result = prime * result + ((urlGuid == null) ? 0 : urlGuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Event)) {
			return false;
		}
		Event other = (Event) obj;
		if (createDate == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!createDate.equals(other.createDate)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (publicFlag != other.publicFlag) {
			return false;
		}
		if (triggerPermission != other.triggerPermission) {
			return false;
		}
		if (urlGuid == null) {
			if (other.urlGuid != null) {
				return false;
			}
		} else if (!urlGuid.equals(other.urlGuid)) {
			return false;
		}
		return true;
	}

}
