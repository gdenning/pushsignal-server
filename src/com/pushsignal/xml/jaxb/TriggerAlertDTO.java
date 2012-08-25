package com.pushsignal.xml.jaxb;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.pushsignal.domain.TriggerAlert;

@XmlRootElement(name="triggerAlert")
@XmlAccessorType(XmlAccessType.FIELD)
public class TriggerAlertDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long triggerAlertId;

	private long triggerId;

	private UserDTO user;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date modifiedDate;
	
	private long modifiedDateInMilliseconds;

	private String status;

	public TriggerAlertDTO() {
	}

	public TriggerAlertDTO(final TriggerAlert triggerAlert) {
		triggerAlertId = triggerAlert.getTriggerAlertId();
		triggerId = triggerAlert.getTrigger().getTriggerId();
		user = new UserDTO(triggerAlert.getUser());
		modifiedDate = triggerAlert.getModifiedDate();
		modifiedDateInMilliseconds = triggerAlert.getModifiedDate().getTime();
		status = triggerAlert.getStatus().name();
	}

	public void setTriggerAlertId(long triggerAlertId) {
		this.triggerAlertId = triggerAlertId;
	}

	public long getTriggerAlertId() {
		return this.triggerAlertId;
	}

	public void setTriggerId(long triggerId) {
		this.triggerId = triggerId;
	}

	public long getTriggerId() {
		return triggerId;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
		this.modifiedDateInMilliseconds = modifiedDate.getTime();
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public long getModifiedDateInMilliseconds() {
		return modifiedDateInMilliseconds;
	}

	public void setStatus(TriggerAlert.TriggerAlertStatusEnum status) {
		this.status = status.toString();
	}

	public TriggerAlert.TriggerAlertStatusEnum getStatus() {
		return TriggerAlert.TriggerAlertStatusEnum.valueOf(this.status);
	}
}
