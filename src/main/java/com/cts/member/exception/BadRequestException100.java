package com.cts.member.exception;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cts.member.response.MemberResponse;

public class BadRequestException100 extends WebApplicationException{
	
	private static final long serialVersionUID = 2983888915506635606L;

	public BadRequestException100(String messageCode, String message, Map<String, String> errorMap) {
		super(Response.status(Status.BAD_REQUEST)
						.entity( new MemberResponse(messageCode,message,errorMap))
						.build());
		
		
	}

	
}
