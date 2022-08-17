package com.org.acme.patient.onboarding.workflow;

import com.org.acme.patient.onboarding.activity.IOnboardingActivity;
import com.org.acme.patient.onboarding.config.WorkflowConfigProperties;
import com.org.acme.patient.onboarding.util.BeanUtil;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.acme.patient.onboarding.model.Patient;

import java.time.Duration;

@Slf4j
public class OnboardingWorkflowImpl implements  IOnboardingWorkflow{

    private WorkflowConfigProperties workflowConfigProperties;

    private final ActivityOptions options ;

    private IOnboardingActivity serviceExecutor;

    private String status;

    private Patient onboardingPatient;

    public OnboardingWorkflowImpl(){

        workflowConfigProperties= BeanUtil.getBean(WorkflowConfigProperties.class);

        RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(workflowConfigProperties.getInitialInterval()))
                .setMaximumInterval(Duration.ofSeconds(workflowConfigProperties.getMaxInterval())).setBackoffCoefficient(2).setMaximumAttempts(workflowConfigProperties.getMaxAttempts()).build();

        options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(workflowConfigProperties.getStartToCloseTimeout()))
                .setRetryOptions(retryoptions).build();

        serviceExecutor= Workflow.newActivityStub(IOnboardingActivity.class,this.options);
    }

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;
        log.info("-------OnboardingImpl : onboardNewPatient---------------");
        try {
            // 1. assign hospital to patient
            status = "Assigning hospital to patient: " + onboardingPatient.getName();

            onboardingPatient.setHospital(
                    serviceExecutor.assignHospitalToPatient(onboardingPatient.getZip()));

            // 2. assign doctor to patient
            status = "Assigning doctor to patient: " + onboardingPatient.getName();
            onboardingPatient.setDoctor(
                    serviceExecutor.assignDoctorToPatient(onboardingPatient.getCondition()));

            // 3. notify patient with preferred contact method
            status = "Notifying patient: " + onboardingPatient.getName();
            switch (onboardingPatient.getContactMethod()) {
                case PHONE:
                    serviceExecutor.notifyViaEmail(onboardingPatient.getEmail());
                    break;

                case TEXT:
                    serviceExecutor.notifyViaText(onboardingPatient.getPhone());
                    break;
            }

            // 4. finalize onboarding
            status = "Finalizing onboarding for: " + onboardingPatient.getName();
            patient.setOnboarded(
                    serviceExecutor.finalizeOnboarding());

        } catch (Exception e) {
            patient.setOnboarded("no");
        }

        return onboardingPatient;
    }

    @Override
    public String getStatus() {
        return status;
    }

}
