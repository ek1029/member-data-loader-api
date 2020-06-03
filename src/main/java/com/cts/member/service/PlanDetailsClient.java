package com.cts.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cts.member.model.MemberDetail;
import com.cts.member.model.Plans;
import com.cts.member.response.MemberResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class PlanDetailsClient {

	Logger logger = LoggerFactory.getLogger(PlanDetailsClient.class);	
	
	@Autowired
	RestTemplate restTemplet; 
	
	@Value("${plan.fetch.plan.detail.url}")
	String planDetailUrl;
	
	
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
		
		Plans plan = new Plans();
		plan.setPlanName("N/A");
		plan.setStatus("N/A");
		return plan;
	}
	
}
