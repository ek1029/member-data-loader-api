package com.cts.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.member.dao.MemberDao;
import com.cts.member.model.MemberDetail;
import com.cts.member.model.RequestDetails;
import com.cts.member.response.MemberResponse;
import com.cts.member.util.MemberConstants;
import com.cts.member.util.MemberUtils;


@Service
public class MemberDetailServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(MemberDetailServiceImpl.class);
  
	@Autowired
	MemberDao memberDao;
	
	
	
	@Value("${excel.filepath}")
	String fileUrl;
	
	 @Autowired HttpServletRequest request;
	
	@Autowired
	PlanDetails planDetail;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity<MemberResponse> createMemberDetail(String fileName, String planId, MemberResponse resp) {
		
		
		List<MemberDetail> memberList =  MemberUtils.readXml(fileUrl+fileName);
		
		resp = validateMember(memberList,resp);
		if(resp.getErrorMap().isEmpty()) {
			String authorizationToken = request.getHeader("Authorization").substring(7);
			planDetail.fetchPlanDetails(memberList, authorizationToken, resp);
		
			if(resp.getErrorMap().isEmpty()) {
				List<MemberDetail> persistedMemberList =	memberDao.persistMember(memberList);
				resp.setMembersDetail(persistedMemberList);
				resp.setMessage("SUCCESS");
				resp.setMessageCode("200");
				return new ResponseEntity<MemberResponse>(resp,HttpStatus.OK);
				
			}else {
				resp.setMessage(MemberConstants.CONFLICT_MESSAGE);
				resp.setMessageCode(MemberConstants.CONFLICT_CODE);
				return new ResponseEntity<MemberResponse>(resp,HttpStatus.CONFLICT);
			}
		}else {
			resp.setMessageCode(MemberConstants.CONFLICT_CODE);
			resp.setMessage(MemberConstants.CONFLICT_MESSAGE);
			return new ResponseEntity<MemberResponse>(resp,HttpStatus.CONFLICT);
		}
		
	}

	public void saveRequestDetails(String requesterId,String fileName) {
		
		RequestDetails rd = new RequestDetails();
		rd.setFileName(fileName);
		rd.setRequesterId(requesterId);
	    memberDao.persistRequestDetail(rd);
	}
	
	
	
	private MemberResponse validateMember(List<MemberDetail> memberList, MemberResponse resp) {
		
		for(MemberDetail member : memberList) {
			if(memberDao.isMemberExists(member.getMemberId()))
				resp.getErrorMap().put(MemberConstants.MEMBER_ID+" "+ member.getMemberId() ,  MemberConstants.MEMBER_ALREADY_EXISTS);
		}
		return resp;
	}
	
	
}
