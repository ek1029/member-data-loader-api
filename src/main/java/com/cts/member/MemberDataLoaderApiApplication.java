package com.cts.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;


@SpringBootApplication
@EnableCircuitBreaker

public class MemberDataLoaderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberDataLoaderApiApplication.class, args);
	}

}
