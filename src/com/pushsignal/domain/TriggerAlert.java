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

		StringBuilder buffer = new StringBuilder();

		buffer.append("triggerAlertId=[").append(triggerAlertId).append("] ");
		buffer.append("modifiedDate=[").append(modifiedDate).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((trigger == null) ? 0 : trigger.hashCode());
		result = prime * result
				+ (int) (triggerAlertId ^ (triggerAlertId >>> 32));
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
		TriggerAlert other = (TriggerAlert) obj;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (trigger == null) {
			if (other.trigger != null)
				return false;
		} else if (!trigger.equals(other.trigger))
			return false;
		if (triggerAlertId != other.triggerAlertId)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public enum TriggerAlertStatusEnum {
		ACTIVE,
		SILENCED,
		ACKNOWLEDGED;
	}
}
