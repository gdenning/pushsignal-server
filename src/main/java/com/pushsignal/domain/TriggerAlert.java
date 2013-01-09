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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllTriggerAlerts", query = "select myTriggerAlert from TriggerAlert myTriggerAlert")
})
@Table(catalog = "PushSignal", name = "TTriggerAlert")
public class TriggerAlert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "TriggerAlertID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long triggerAlertId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "TriggerID", referencedColumnName = "TriggerID", nullable = false) })
	@ForeignKey(name = "FK_TriggerAlert_Trigger")
	private Trigger trigger;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false) })
	@ForeignKey(name = "FK_TriggerAlert_User")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ModifiedDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date modifiedDate;
	
	@Column(name="Status", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String status;

	public void setTriggerAlertId(long triggerAlertId) {
		this.triggerAlertId = triggerAlertId;
	}

	public long getTriggerAlertId() {
		return this.triggerAlertId;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setStatus(TriggerAlertStatusEnum status) {
		this.status = status.toString();
	}

	public TriggerAlertStatusEnum getStatus() {
		return TriggerAlertStatusEnum.valueOf(this.status);
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("triggerAlertId", triggerAlertId)
			.append("trigger", trigger)
			.append("user", user)
			.append("modifiedDate", modifiedDate)
			.append("status", status)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(trigger)
			.append(user)
			.append(modifiedDate)
			.append(status)
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
		TriggerAlert other = (TriggerAlert) obj;
		return new EqualsBuilder()
			.append(trigger, other.trigger)
			.append(user, other.user)
			.append(modifiedDate, other.modifiedDate)
			.append(status, other.status)
			.isEquals();
	}

	public enum TriggerAlertStatusEnum {
		ACTIVE,
		SILENCED,
		ACKNOWLEDGED;
	}
}
