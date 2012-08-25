package com.pushsignal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@NamedQueries({
		@NamedQuery(name = "findEventInvitesByEmail", query = "select myEventInvite from EventInvite myEventInvite where myEventInvite.email = ?1"),
		@NamedQuery(name = "findEventInvitesByUserId", query = "select myEventInvite from EventInvite myEventInvite where myEventInvite.user.userId = ?1")
})
@Table(catalog = "PushSignal", name = "TEventInvite")
public class EventInvite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EventInviteID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eventInviteId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "EventID", referencedColumnName = "EventID", nullable = false) })
	@ForeignKey(name = "FK_EventInvite_Event")
	private Event event;

	@Column(name = "Email")
	@Basic(fetch = FetchType.EAGER)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "CreatedByUserID", referencedColumnName = "UserID") })
	@ForeignKey(name = "FK_EventInvite_CreatedBy")
	private User createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "UserID", referencedColumnName = "UserID") })
	@ForeignKey(name = "FK_EventInvite_User")
	private User user;

	public void setEventInviteId(long eventInviteId) {
		this.eventInviteId = eventInviteId;
	}

	public long getEventInviteId() {
		return this.eventInviteId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("eventInviteId=[").append(eventInviteId).append("] ");
		buffer.append("email=[").append(email).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		EventInvite other = (EventInvite) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
