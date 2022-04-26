package com.org.acme.patient.onboarding.service;


import com.org.acme.patient.onboarding.event.WorkflowApplicationObserver;

import com.org.acme.patient.onboarding.workflow.IOnboardingWorkflow;
import io.temporal.client.WorkflowOptions;
import org.acme.patient.onboarding.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OnboardingServiceImpl implements IOnboardingService{

    @Autowired
    WorkflowApplicationObserver observer;

    @Value("${onboarding.task.queue}")
    private String taskQueue;


    @Override
    public Patient doOnboard(Patient patient) {
        System.out.print("-------Starting Patient Onboarding---------------");
        // start a new workflow execution
        // use the patient id for the unique id
        IOnboardingWorkflow workflow =
                observer.getClient().newWorkflowStub(
                        IOnboardingWorkflow.class, WorkflowOptions.newBuilder()
                                .setWorkflowId(patient.getId())
                                .setTaskQueue(taskQueue).build());

        return workflow.onboardNewPatient(patient);
    }

    @Override
    public String getStatus(String patientId) {
        try {
            IOnboardingWorkflow workflow = observer.getClient().newWorkflowStub(IOnboardingWorkflow.class, patientId);
            return workflow.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to query workflow with id: " + patientId;
        }
    }
}
