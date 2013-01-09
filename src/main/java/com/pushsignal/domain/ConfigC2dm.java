package com.pushsignal.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("configId", configId)
			.append("authToken", authToken)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(authToken)
			.append(password)
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
		if (!(obj instanceof ConfigC2dm)) {
			return false;
		}
		ConfigC2dm other = (ConfigC2dm) obj;
		return new EqualsBuilder()
			.append(authToken, other.authToken)
			.append(password, other.password)
			.isEquals();
	}
}
