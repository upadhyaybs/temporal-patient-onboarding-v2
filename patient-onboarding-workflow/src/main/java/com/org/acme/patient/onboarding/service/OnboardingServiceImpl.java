package com.org.acme.patient.onboarding.service;



import com.org.acme.patient.onboarding.workflow.IOnboardingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.extern.slf4j.Slf4j;
import org.acme.patient.onboarding.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnboardingServiceImpl implements IOnboardingService{

    @Autowired
    WorkflowClient workflowClient;

    @Override
    public Patient doOnboard(Patient patient) {
        log.info("-------Starting Patient Onboarding---------------");
        // start a new workflow execution
        // use the patient id for the unique id
        IOnboardingWorkflow workflow = createWorkFlowConnection(patient.getId());
        //WorkflowClient.start(workflow::onboardNewPatient,patient);
        return workflow.onboardNewPatient(patient);
    }

    @Override
    public String getStatus(String patientId) {
        log.info("-------Get Status---------------");
        try {
            IOnboardingWorkflow workflow = workflowClient.newWorkflowStub(IOnboardingWorkflow.class,IOnboardingWorkflow.WORKFLOW_ID+"_" + patientId);
            return workflow.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to query workflow with id: " + patientId;
        }
    }

    public IOnboardingWorkflow createWorkFlowConnection(String id) {
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(IOnboardingWorkflow.QUEUE_NAME)
                .setWorkflowId(IOnboardingWorkflow.WORKFLOW_ID+"_" + id).build();
        return workflowClient.newWorkflowStub(IOnboardingWorkflow.class, options);
    }
}
