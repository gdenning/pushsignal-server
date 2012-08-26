package com.pushsignal.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResultDTO {
	private String description;

	public ErrorResultDTO() {
	}

	public ErrorResultDTO(final String description) {
		setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String errorDescription) {
		this.description = errorDescription;
	}
}
