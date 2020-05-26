package com.cts.member.exception;

import org.springframework.http.HttpStatus;

public class ExceptionDetails {
	
	private final String errorMessage;
	private final String errorCode;
	//private final Throwable throwable;
	
	

	

	public ExceptionDetails(String errorMessage, String errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
