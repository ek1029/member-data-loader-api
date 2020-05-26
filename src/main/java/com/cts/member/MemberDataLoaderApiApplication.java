package com.cts.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cts.member.service.MemberDetailServiceImpl;

@SpringBootApplication

public class MemberDataLoaderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberDataLoaderApiApplication.class, args);
	}

}
