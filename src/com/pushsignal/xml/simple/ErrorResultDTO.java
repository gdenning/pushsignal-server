package com.pushsignal.xml.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class ErrorResultDTO {
	@Element
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
