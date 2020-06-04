package com.cts.member.resource;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.member.request.MemberRequest;
import com.cts.member.response.MemberResponse;
import com.cts.member.service.MemberDetailServiceImpl;
import com.cts.member.util.JWTUtils;
import com.cts.member.util.MemberConstants;
import com.cts.member.util.MemberUtils;



@RestController
@RequestMapping("/member")
public class MemberResource {

	private static final Logger logger = LoggerFactory.getLogger(MemberResource.class);
	
	@Autowired
	JWTUtils jwtUtils;
	
	@Autowired
	MemberDetailServiceImpl memberDetailServiceImpl;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	  @PostMapping(consumes = "application/json", produces = "application/json")
	  public ResponseEntity<MemberResponse>  createMember(@RequestBody MemberRequest memberRequest) {
		  
		  MemberResponse resp = new MemberResponse();
		
		  MemberUtils.validateMandatory(memberRequest, resp.getErrorMap());
		  if(resp.getErrorMap().isEmpty()) {
			  memberDetailServiceImpl.saveRequestDetails(memberRequest.getRequesterId(), memberRequest.getFileName());
		  }else {
			  resp.setMessage(MemberConstants.BAD_REQUEST_MESSAGE);
			  resp.setMessageCode(MemberConstants.BAD_REQUEST_CODE);
			  return new ResponseEntity<MemberResponse>(resp,HttpStatus.BAD_REQUEST);
		  }
    	 return  memberDetailServiceImpl.createMemberDetail(memberRequest.getFileName(),  memberRequest.getRequesterId(), resp);
	  }
	  
}
