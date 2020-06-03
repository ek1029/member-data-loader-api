package com.cts.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.member.model.MemberDetail;
import com.cts.member.model.Plans;
import com.cts.member.response.MemberResponse;
import com.cts.member.util.MemberConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class PlanDetails {

	
Logger logger = LoggerFactory.getLogger(PlanDetails.class);	
	
	
	@Autowired
	HttpHeaders httpHeaders;
	
	@Autowired
	PlanDetailsClient planDetailsClient;
	
	public void fetchPlanDetails(List<MemberDetail> memberList, String authorizationToken, MemberResponse resp) {
		
		HttpEntity request = enrichHttpEntity(authorizationToken); 
		for(MemberDetail mem: memberList){
			Plans plan = planDetailsClient.getPlanDetailById(resp, request, mem);
			if(null!=plan) {
				mem.setPlanName(plan.getPlanName());
				mem.setPlanStatus(plan.getStatus());
			}else {
				resp.getErrorMap().put("PlanId"+" "+mem.getPlanId(), MemberConstants.INVALID_PLAN);
			}
		}
	}


	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HttpEntity enrichHttpEntity(String authorizationToken) {
		httpHeaders.setBearerAuth(authorizationToken);
		return new HttpEntity(httpHeaders);
		
		
	}
}
