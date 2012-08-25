package com.pushsignal.xml.simple;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="userDevice", strict=false)
public class UserDeviceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private long userDeviceId;

	@Element
	private UserDTO user;

	@Element
	private String deviceType;

	@Element
	private String deviceId;

	@Element
	private String registrationId;
	
	@Element
	private long serverMillisecondsSince1970;
	
	private long clientMillisecondsSinceBoot;
	
	public void setUserDeviceId(long userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public long getUserDeviceId() {
		return userDeviceId;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
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

	public void setServerMillisecondsSince1970(long serverMillisecondsSince1970) {
		this.serverMillisecondsSince1970 = serverMillisecondsSince1970;
	}

	public long getServerMillisecondsSince1970() {
		return serverMillisecondsSince1970;
	}

	public void setClientMillisecondsSinceBoot(long clientMillisecondsSinceBoot) {
		this.clientMillisecondsSinceBoot = clientMillisecondsSinceBoot;
	}

	public long getClientMillisecondsSinceBoot() {
		return clientMillisecondsSinceBoot;
	}
}
