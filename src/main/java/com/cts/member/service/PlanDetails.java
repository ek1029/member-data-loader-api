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
	/*
	 * @Autowired HttpServletRequest request;
	 */
	
	@Autowired
	RestTemplate restTemplet; 
	
	@Autowired
	HttpHeaders httpHeaders;
	
	@Value("${plan.fetch.plan.detail.url}")
	String planDetailUrl;
	
	
	public void fetchPlanDetails(List<MemberDetail> memberList, String authorizationToken, MemberResponse resp) {
		
		HttpEntity request = enrichHttpEntity(authorizationToken, httpHeaders); 
		for(MemberDetail mem: memberList){
			Plans plan = getPlanDetailById(resp, request, mem);
			if(null!=plan) {
				mem.setPlanName(plan.getPlanName());
				mem.setPlanStatus(plan.getStatus());
			}else {
				resp.getErrorMap().put("PlanId"+" "+mem.getPlanId(), MemberConstants.INVALID_PLAN);
			}
		}
	}

	@HystrixCommand(fallbackMethod = "getFallbackPlanById")
	public Plans getPlanDetailById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		ResponseEntity<Plans> planResponseEntity = restTemplet.exchange(planDetailUrl+mem.getPlanId(),HttpMethod.GET,request ,Plans.class);
		if(planResponseEntity.getBody() != null) {
			return planResponseEntity.getBody();
		}
		return null;
	}
	
	public Plans getFallbackPlanById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		logger.error("<<< Plan details from PlanFallback !!! >>>");
		Plans plan=null;
		plan = new Plans();
		mem.setPlanName("N/A");
		mem.setPlanStatus("N/A");
		return plan;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HttpEntity enrichHttpEntity(String authorizationToken, HttpHeaders httpHeader) {
		httpHeaders.setBearerAuth(authorizationToken);
		return new HttpEntity(httpHeaders);
		
		
	}
}
