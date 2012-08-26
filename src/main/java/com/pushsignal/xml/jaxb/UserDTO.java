package com.pushsignal.xml.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pushsignal.domain.User;

@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long userId;

	private String name;
	
	private String password;

	private String description;
	
	private long points;

	public UserDTO() {
	}
	
	public UserDTO(final User user) {
		this.userId = user.getUserId();
		this.name = user.getName();
		this.password = user.getUnencryptedPassword();
		this.description = user.getDescription();
	}
	
	public UserDTO(final User user, long points) {
		this.userId = user.getUserId();
		this.name = user.getName();
		this.password = user.getUnencryptedPassword();
		this.description = user.getDescription();
		this.points = points;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public long getPoints() {
		return points;
	}
}
