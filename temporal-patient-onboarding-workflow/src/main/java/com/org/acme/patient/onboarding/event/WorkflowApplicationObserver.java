package com.org.acme.patient.onboarding.event;

import com.org.acme.patient.onboarding.activity.ServiceExecutorImpl;
import com.org.acme.patient.onboarding.restclient.OnboardingServiceClient;
import com.org.acme.patient.onboarding.workflow.OnboardingWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class WorkflowApplicationObserver {

    private WorkflowClient client;

    private WorkerFactory factory;

    @Autowired
    OnboardingServiceClient serviceClient;

    @Value("${onboarding.task.queue}")
    private String taskQueue;

    @PostConstruct
    void onStart(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        client = WorkflowClient.newInstance(service);
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

}
