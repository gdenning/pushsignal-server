package com.pushsignal.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteResultDTO {
	private String objectType;

	public DeleteResultDTO() {
	}

	public DeleteResultDTO(final String objectType) {
		setObjectType(objectType);
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(final String objectType) {
		this.objectType = objectType;
	}

	public String getResult() {
		return "Successfully deleted " + objectType;
	}
}
