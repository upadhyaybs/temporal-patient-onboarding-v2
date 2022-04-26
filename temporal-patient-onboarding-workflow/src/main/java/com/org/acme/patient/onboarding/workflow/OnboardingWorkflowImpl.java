package com.org.acme.patient.onboarding.workflow;

import com.org.acme.patient.onboarding.activity.ServiceExecutor;
import com.org.acme.patient.onboarding.util.ActivityStubUtils;
import org.acme.patient.onboarding.model.Patient;

public class OnboardingWorkflowImpl implements  IOnboardingWorkflow{

    ServiceExecutor serviceExecutor = ActivityStubUtils.getActivitiesStub();

    String status;
    Patient onboardingPatient;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;
        System.out.print("-------OnboardingImpl : onboardNewPatient---------------");
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
