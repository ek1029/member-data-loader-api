package com.cts.member.exception;

public class ConflictException extends RuntimeException{

	
	private static final long serialVersionUID = -7497583738436050881L;

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictException(String message) {
		super(message);
	}
	
}
