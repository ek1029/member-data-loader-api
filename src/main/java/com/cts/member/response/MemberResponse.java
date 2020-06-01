package com.cts.member.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cts.member.model.MemberDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberResponse {

	protected String messageCode;
	protected String message;
	protected List<MemberDetail> membersDetail;
	protected Map<String,String> errorMap;
	
	public MemberResponse(String messageCode, String message, Map<String, String> errorMap) {
		this.messageCode = messageCode;
		this.message = message;
		this.errorMap = errorMap;
	}
	
	public Map<String,String> getErrorMap(){
		if(errorMap==null) {
			errorMap = new HashMap<String,String>();
		}
		return errorMap;
	}
	public void setErrorMap(Map<String,String> errorMap) {
		this.errorMap = errorMap;
	}
	
}
