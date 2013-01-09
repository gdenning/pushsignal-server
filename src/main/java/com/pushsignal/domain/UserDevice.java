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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(catalog = "PushSignal", name = "TUserDevice")
public class UserDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "UserDeviceID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userDeviceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "UserID", referencedColumnName = "UserID") })
	@ForeignKey(name = "FK_UserDevice_User")
	private User user;

	@Column(name = "DeviceType", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String deviceType;

	@Column(name = "DeviceID", length = 1024, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String deviceId;

	@Column(name = "RegistrationID", length = 1024, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String registrationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastSeenDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date lastSeenDate;

	public void setUserDeviceId(long userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public long getUserDeviceId() {
		return userDeviceId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}

	public Date getLastSeenDate() {
		return lastSeenDate;
	}
	
	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("userDeviceId", userDeviceId)
			.append("user", user)
			.append("deviceType", deviceType)
			.append("deviceId", deviceId)
			.append("registrationId", registrationId)
			.append("lastSeenDate", lastSeenDate)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(user)
			.append(deviceType)
			.append(deviceId)
			.append(registrationId)
			.append(lastSeenDate)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserDevice)) {
			return false;
		}
		UserDevice other = (UserDevice) obj;
		return new EqualsBuilder()
			.append(user, other.user)
			.append(deviceType, other.deviceType)
			.append(deviceId, other.deviceId)
			.append(registrationId, other.registrationId)
			.append(lastSeenDate, other.lastSeenDate)
			.isEquals();
	}
}
