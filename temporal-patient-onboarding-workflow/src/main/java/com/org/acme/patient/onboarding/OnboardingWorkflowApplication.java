package com.org.acme.patient.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OnboardingWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingWorkflowApplication.class, args);
	}

}
