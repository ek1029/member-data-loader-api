package com.cts.member.exception;

public class NotFoundException  extends RuntimeException{
	
	private static final long serialVersionUID = -5273142681393354402L;

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	
}
