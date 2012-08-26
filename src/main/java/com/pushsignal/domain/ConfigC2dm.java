package com.pushsignal.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "PushSignal", name = "TConfigC2dm")
public class ConfigC2dm {
    @Column(name = "ConfigId", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	private long configId;

    @Column(name = "Password", length = 255, nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private String password;

	@Column(name = "AuthToken", length = 255, nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private String authToken;

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public long getConfigId() {
		return configId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authToken == null) ? 0 : authToken.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		if (!(obj instanceof ConfigC2dm)) {
			return false;
		}
		ConfigC2dm other = (ConfigC2dm) obj;
		if (authToken == null) {
			if (other.authToken != null) {
				return false;
			}
		} else if (!authToken.equals(other.authToken)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}
}
