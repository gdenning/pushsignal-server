package com.pushsignal.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends PushSignalException {
	public ResourceNotFoundException(final String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}