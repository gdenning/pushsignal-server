package com.pushsignal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@NamedQueries({
		@NamedQuery(name = "findActiveTriggersForUser", query = "select myTrigger from Trigger myTrigger JOIN myTrigger.triggerAlerts myTriggerAlert where myTriggerAlert.user.userId = ?1 and myTriggerAlert.status = 'ACTIVE'")
})
@Table(catalog = "PushSignal", name = "TTrigger")
public class Trigger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "TriggerID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long triggerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "EventID", referencedColumnName = "EventID", nullable = false) })
	@ForeignKey(name = "FK_Trigger_Event")
	private Event event;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "CreateUserID", referencedColumnName = "UserID", nullable = true) })
	@ForeignKey(name = "FK_Trigger_CreatedBy")
	private User user;
	
	@Column(name = "message", length = 4000)
	@Basic(fetch = FetchType.EAGER)
	private String message;
	
	@OneToMany(mappedBy = "trigger", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@ForeignKey(name = "FK_TriggerAlert_Trigger")
	private Set<TriggerAlert> triggerAlerts;

	public void setTriggerId(long triggerId) {
		this.triggerId = triggerId;
	}

	public long getTriggerId() {
		return this.triggerId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public boolean isMember(final User user) {
		boolean userFound = false;
		for (TriggerAlert alert : getTriggerAlerts()) {
			if (alert.getUser().equals(user)) {
				userFound = true;
			}
		}
		return userFound;
	}
	
	public TriggerAlert getTriggerAlertForUser(final User user) {
		for (TriggerAlert alert : getTriggerAlerts()) {
			if (alert.getUser().equals(user)) {
				return alert;
			}
		}
		return null;
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("triggerId", triggerId)
			.append("createDate", createDate)
			.append("event", event)
			.append("user", user)
			.append("message", message)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(createDate)
			.append(event)
			.append(user)
			.append(message)
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
		Trigger other = (Trigger) obj;
		return new EqualsBuilder()
			.append(createDate, other.createDate)
			.append(event, other.event)
			.append(user, other.user)
			.append(message, other.message)
			.isEquals();
	}

}
