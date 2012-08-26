package com.pushsignal.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends PushSignalException {
	public InternalServerErrorException(final Throwable throwable) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
	}
}
