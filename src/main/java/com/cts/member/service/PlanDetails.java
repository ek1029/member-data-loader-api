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
		Plans plan = null;
		//String authorizationToken = request.getHeader("Authorization").substring(7);
		HttpEntity request = enrichHttpEntity(authorizationToken, httpHeaders); 
		for(MemberDetail mem: memberList){
			
			getPlanById(resp, request, mem);
		}
		
	}

	@HystrixCommand(fallbackMethod = "getFallPlanById")
	public Plans getPlanById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		Plans plan = null;
		ResponseEntity<Plans> planResponseEntity = restTemplet.exchange(planDetailUrl+mem.getPlanId(),HttpMethod.GET,request ,Plans.class);
		if(planResponseEntity.getBody() != null) {
			plan = planResponseEntity.getBody();
			mem.setPlanName(plan.getPlanName());
			mem.setPlanStatus(plan.getStatus());
		}else {
			resp.getErrorMap().put("PlanId"+" "+mem.getPlanId(), MemberConstants.INVALID_PLAN);
		}
		return plan;
	}
	
	public Plans getFallPlanById(MemberResponse resp, HttpEntity request, MemberDetail mem) {
		logger.error("<<< Plan details from PlanFallback !!! >>>");
		Plans plan=null;
		
			plan = new Plans();
			/*
			 * mem.setPlanName(plan.getPlanName()); mem.setPlanStatus(plan.getStatus());
			 */
			
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
