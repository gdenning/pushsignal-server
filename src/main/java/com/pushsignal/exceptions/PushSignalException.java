package com.pushsignal.exceptions;

import org.springframework.http.HttpStatus;

public abstract class PushSignalException extends RuntimeException {
	private HttpStatus httpStatus;
	
	public PushSignalException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus; 
	}
	
	public PushSignalException(HttpStatus httpStatus, Throwable throwable) {
		super(throwable);
		this.httpStatus = httpStatus; 
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
