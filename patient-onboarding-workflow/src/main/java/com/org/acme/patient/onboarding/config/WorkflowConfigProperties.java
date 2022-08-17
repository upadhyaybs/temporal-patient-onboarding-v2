package com.org.acme.patient.onboarding.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration
public class WorkflowConfigProperties {

   @Value("${workflow.retry.options.initialInterval}")
   private long initialInterval;

   @Value("${workflow.retry.options.maxInterval}")
   private long maxInterval;

   @Value("${workflow.retry.options.backoffCoefficient}")
   private long backoffCoefficient;

   @Value("${workflow.retry.options.maxAttempts}")
   private int maxAttempts;

   @Value("${workflow.activity.options.startToCloseTimeout}")
   private long startToCloseTimeout;

}
