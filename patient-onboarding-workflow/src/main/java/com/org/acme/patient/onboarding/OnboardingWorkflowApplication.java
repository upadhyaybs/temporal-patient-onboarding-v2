package com.org.acme.patient.onboarding;

import com.org.acme.patient.onboarding.activity.IOnboardingActivity;
import com.org.acme.patient.onboarding.workflow.IOnboardingWorkflow;
import com.org.acme.patient.onboarding.workflow.OnboardingWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class OnboardingWorkflowApplication {


	public static void main(String[] args) {

		ConfigurableApplicationContext appContext = SpringApplication.run(OnboardingWorkflowApplication.class, args);

		WorkerFactory factory = appContext.getBean(WorkerFactory.class);

		IOnboardingActivity onboardingActivity = appContext.getBean(IOnboardingActivity.class);

		Worker worker = factory.newWorker(IOnboardingWorkflow.QUEUE_NAME);

		worker.registerWorkflowImplementationTypes(OnboardingWorkflowImpl.class);
		worker.registerActivitiesImplementations(onboardingActivity);
		factory.start();
	}

}
