package com.org.acme.patient.onboarding.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;

@ActivityInterface
public interface IOnboardingActivity {

    @ActivityMethod(name = "assignHospitalToPatient")
    Hospital assignHospitalToPatient(String zip);

    @ActivityMethod(name = "assignDoctorToPatient")
    Doctor assignDoctorToPatient(String condition);

    @ActivityMethod(name = "finalizeOnboarding")
    String finalizeOnboarding();

    @ActivityMethod(name = "notifyViaEmail")
    void notifyViaEmail(String email);

    @ActivityMethod(name = "notifyViaText")
    void notifyViaText(String number);
}
