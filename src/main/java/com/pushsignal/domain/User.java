package com.pushsignal.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllUsers", query = "select myUser from User myUser"),
		@NamedQuery(name = "findUserByNameContaining", query = "select myUser from User myUser where myUser.name like ?1"),
		@NamedQuery(name = "findUserByName", query = "select myUser from User myUser where myUser.name = ?1"),
		@NamedQuery(name = "findUserByEmail", query = "select myUser from User myUser where myUser.email = ?1"),
		@NamedQuery(name = "getUserPoints", query = "select sum(myActivity.points) from Activity myActivity join myActivity.user myUser where myUser.userId = ?1")
})
@Table(catalog = "PushSignal", name = "TUser")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "UserID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(name = "Email", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String email;

	@Column(name = "Password", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String password;
	
	@Transient
	private String unencryptedPassword;

	@Column(name = "Name", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String name;

	@Column(name = "Description", length = 1000, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String description;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@ForeignKey(name = "FK_UserDevice_User")
	private Set<UserDevice> devices;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_TriggerAlert_User")
	private Set<TriggerAlert> triggerAlerts;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_EventInvite_User")
	private Set<EventInvite> eventInvites;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_EventMember_User")
	private Set<EventMember> eventMembers;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Trigger_User")
	private Set<Trigger> triggers;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Event_User")
	private Set<Event> events;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Activity_User")
	private Set<Activity> activities;

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUnencryptedPassword(String unencryptedPassword) {
		this.unencryptedPassword = unencryptedPassword;
	}

	public String getUnencryptedPassword() {
		return unencryptedPassword;
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

	public void setDevices(Set<UserDevice> devices) {
		this.devices = devices;
	}

	public Set<UserDevice> getDevices() {
		if (devices == null) {
			devices = new LinkedHashSet<UserDevice>();
		}
		return devices;
	}

	public void setTriggerAlerts(Set<TriggerAlert> triggerAlerts) {
		this.triggerAlerts = triggerAlerts;
	}

	public Set<TriggerAlert> getTriggerAlerts() {
		if (triggerAlerts == null) {
			triggerAlerts = new LinkedHashSet<TriggerAlert>();
		}
		return triggerAlerts;
	}

	public void setEventInvites(Set<EventInvite> eventInvites) {
		this.eventInvites = eventInvites;
	}

	public Set<EventInvite> getEventInvites() {
		if (eventInvites == null) {
			eventInvites = new LinkedHashSet<EventInvite>();
		}
		return eventInvites;
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

	public void setEventMembers(Set<EventMember> eventMembers) {
		this.eventMembers = eventMembers;
	}

	public Set<EventMember> getEventMembers() {
		if (eventMembers == null) {
			eventMembers = new LinkedHashSet<EventMember>();
		}
		return eventMembers;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<Event> getEvents() {
		if (events == null) {
			events = new LinkedHashSet<Event>();
		}
		return events;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<Activity> getActivities() {
		if (activities == null) {
			activities = new LinkedHashSet<Activity>();
		}
		return activities;
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("userId", userId)
			.append("email", email)
			.append("name", name)
			.append("description", description)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(email)
			.append(name)
			.append(description)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return new EqualsBuilder()
			.append(email, other.email)
			.append(name, other.name)
			.append(description, other.description)
			.isEquals();
	}

}
