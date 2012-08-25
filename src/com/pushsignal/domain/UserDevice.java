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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result
				+ ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result
				+ ((lastSeenDate == null) ? 0 : lastSeenDate.hashCode());
		result = prime * result
				+ ((registrationId == null) ? 0 : registrationId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (!(obj instanceof UserDevice)) {
			return false;
		}
		UserDevice other = (UserDevice) obj;
		if (deviceId == null) {
			if (other.deviceId != null) {
				return false;
			}
		} else if (!deviceId.equals(other.deviceId)) {
			return false;
		}
		if (deviceType == null) {
			if (other.deviceType != null) {
				return false;
			}
		} else if (!deviceType.equals(other.deviceType)) {
			return false;
		}
		if (lastSeenDate == null) {
			if (other.lastSeenDate != null) {
				return false;
			}
		} else if (!lastSeenDate.equals(other.lastSeenDate)) {
			return false;
		}
		if (registrationId == null) {
			if (other.registrationId != null) {
				return false;
			}
		} else if (!registrationId.equals(other.registrationId)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}
}
