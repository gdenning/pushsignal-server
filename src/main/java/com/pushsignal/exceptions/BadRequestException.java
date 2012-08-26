package com.pushsignal.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends PushSignalException {
	public BadRequestException(final String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
