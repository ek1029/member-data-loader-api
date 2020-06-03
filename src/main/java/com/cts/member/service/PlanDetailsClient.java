package com.cts.member.service;

import java.util.HashMap;
import java.util.Map;

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
	
	public static	Map<Integer, Plans> cache ; static { cache =  new HashMap<Integer, Plans>();}
	
	@Autowired
	RestTemplate restTemplet; 
	
	@Value("${plan.fetch.plan.detail.url}")
	String planDetailUrl;
	
	
	@HystrixCommand(fallbackMethod = "getFallbackPlanById")
	public Plans getPlanDetailById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		ResponseEntity<Plans> planResponseEntity = restTemplet.exchange(planDetailUrl+mem.getPlanId(),HttpMethod.GET,request ,Plans.class);
		if(planResponseEntity.getBody() != null) {
			cache.put(mem.getPlanId(), planResponseEntity.getBody());
			return planResponseEntity.getBody();
		}
		return null;
	}
	
	public Plans getFallbackPlanById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		logger.error("<<< Plan details from PlanFallback !!! >>>");
		Plans plan = null;
		if(cache.containsKey(mem.getPlanId())) {
			plan = cache.get(mem.getPlanId());
			return plan;
		}else {
			plan = new Plans();
			plan.setPlanName("N/A");
			plan.setStatus("N/A");
		}
		return plan;
	}
	
}
