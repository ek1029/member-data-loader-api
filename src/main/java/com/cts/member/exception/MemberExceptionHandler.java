package com.cts.member.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.member.model.MemberDetail;
import com.cts.member.response.MemberResponse;
import com.cts.member.util.MemberConstants;

@ControllerAdvice
public class MemberExceptionHandler {

	/*
	 * @ExceptionHandler(value = {BadRequestException.class}) public
	 * ResponseEntity<Object> handleBadRequestException(BadRequestException be){
	 * 
	 * ExceptionDetails ed = new ExceptionDetails(be.getMessage(), "400"); return
	 * new ResponseEntity<Object>(ed, HttpStatus.BAD_REQUEST); }
	 * 
	 * @ExceptionHandler(value = {NotFoundException.class}) public
	 * ResponseEntity<Object> handleBadRequestException(NotFoundException nf){
	 * 
	 * ExceptionDetails ed = new ExceptionDetails(nf.getMessage(), "400"); return
	 * new ResponseEntity<Object>(ed, HttpStatus.BAD_REQUEST); }
	 */
	
	//String messageCode, String message, Map<String, String> errorMap
	/*
	 * @ExceptionHandler(value = {BadRequestException.class}) ResponseEntity<Object>
	 * handleBadRequestException(String messageCode, String message, Map<String,
	 * String> errorMap){ MemberResponse memberResp = new
	 * MemberResponse(messageCode,message, errorMap); return new
	 * ResponseEntity<>(new MemberResponse(messageCode,message, errorMap),
	 * HttpStatus.BAD_REQUEST); }
	 */
}
