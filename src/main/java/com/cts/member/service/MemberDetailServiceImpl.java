package com.cts.member.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.member.dao.MemberDao;
import com.cts.member.model.MemberDetail;
import com.cts.member.model.Plans;
import com.cts.member.model.RequestDetails;
import com.cts.member.response.MemberResponse;
import com.cts.member.util.MemberUtils;


@Service
public class MemberDetailServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(MemberDetailServiceImpl.class);
  
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	RestTemplate restTemplet; 
	
	@Value("${excel.filepath}")
	String fileUrl;
	
	
	@Value("${plan.fetch.plan.detail.url}")
	String planDetailUrl;
	
	
	public MemberResponse createMemberDetail(String fileName, String planId) {
		
		MemberResponse resp = new MemberResponse();
		List<MemberDetail> memberList =  MemberUtils.readXml(fileUrl+fileName);
		
		for(MemberDetail mem: memberList){
			logger.info("members : "+mem);
					 
			Plans plan = restTemplet.getForObject(planDetailUrl+mem.getPlanId(), Plans.class);
			mem.setPlanName(plan.getPlanName());
			mem.setPlanStatus(plan.getStatus());
		}
		List<MemberDetail> persistedMemberList =	memberDao.persistMember(memberList);
			resp.setMembersDetail(persistedMemberList);
			resp.setMembersDetail(memberList);
			resp.setMessage("SUCCESS");
			resp.setMessageCode("200");
			
		return resp;
	}

	public void saveRequestDetails(String requesterId,String fileName) {
		
		RequestDetails rd = new RequestDetails();
		rd.setFileName(fileName);
		rd.setRequesterId(requesterId);
	    memberDao.persistRequestDetail(rd);
	}
	
}
