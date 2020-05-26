package com.cts.member.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.cts.member.util.JWTUtils;

@Configuration
public class MemberConfiguration {

	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public JWTUtils jwtUtils() {
		return new JWTUtils();
	}
}
