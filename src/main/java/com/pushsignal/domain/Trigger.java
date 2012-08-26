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

		StringBuilder buffer = new StringBuilder();

		buffer.append("triggerId=[").append(triggerId).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
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
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
