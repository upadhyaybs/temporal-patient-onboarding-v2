package com.org.acme.patient.onboarding.config;

import com.org.acme.patient.onboarding.activity.IOnboardingActivity;
import com.org.acme.patient.onboarding.activity.OnboardingActivityImpl;
import com.org.acme.patient.onboarding.restclient.OnboardingServiceClient;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;

import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class TemporalConfig {

    @Autowired
    OnboardingServiceClient serviceClient;

    @Value("${temporal.server.address}")
    private String temporalServerAddress;

    @Value("${temporal.namespace}")
    private String temporalNamespace ;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs
                .newInstance(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalServerAddress).build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        return WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public IOnboardingActivity SignUpActivity() {
        return new OnboardingActivityImpl(serviceClient);
    }

    /*

    private WorkerFactory factory;

    @PostConstruct
    void onStart(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueue);

        worker.registerWorkflowImplementationTypes(OnboardingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ServiceExecutorImpl(serviceClient));

        factory.start();
    }

    @PreDestroy
    void onStop(){
        factory.shutdown();
    }

    public WorkflowClient getClient() {
        return client;
    }
    */
}
