package com.pushsignal.xml.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class DeleteResultDTO {
	@Element
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
