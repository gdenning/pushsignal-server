package com.pushsignal.xml.jaxb;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.UserDevice;

@XmlRootElement(name="userDevice")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDeviceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long userDeviceId;

	private UserDTO user;

	private String deviceType;

	private String deviceId;

	private String registrationId;
	
	private long serverMillisecondsSince1970;
	
	public UserDeviceDTO() {
	}
	
	public UserDeviceDTO(final UserDevice userDevice) {
		userDeviceId = userDevice.getUserDeviceId();
		user = new UserDTO(userDevice.getUser());
		deviceType = userDevice.getDeviceType();
		deviceId = userDevice.getDeviceId();
		registrationId = userDevice.getRegistrationId();
		serverMillisecondsSince1970 = (new Date()).getTime();
	}

	public UserDeviceDTO(final UserDevice userDevice, long points) {
		userDeviceId = userDevice.getUserDeviceId();
		user = new UserDTO(userDevice.getUser(), points);
		deviceType = userDevice.getDeviceType();
		deviceId = userDevice.getDeviceId();
		registrationId = userDevice.getRegistrationId();
		serverMillisecondsSince1970 = (new Date()).getTime();
	}

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
}
