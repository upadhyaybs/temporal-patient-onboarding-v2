package com.org.acme.patient.onboarding.activity;

import com.org.acme.patient.onboarding.restclient.OnboardingServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;

@Slf4j
public class OnboardingActivityImpl implements IOnboardingActivity {

    private OnboardingServiceClient serviceClient;

    public OnboardingActivityImpl(OnboardingServiceClient serviceClient){
        this.serviceClient=serviceClient;
    }

    @Override
    public Hospital assignHospitalToPatient(String zip) {
        log.info("-------Assign Hospital---------------");
        // call onboarding service to assign hospital
        Hospital hospital = serviceClient.assignHospitalToPatient(zip);
        return hospital;
    }

    @Override
    public Doctor assignDoctorToPatient(String condition) {
        log.info("-------Assign Doctor---------------");
        // call onboarding service to assign doctor
        Doctor doctor = serviceClient.assignDoctorToPatient(condition);
        return doctor;
    }

    @Override
    public void notifyViaEmail(String email) {
        log.info("-------Notify via email---------------");
        try {
            serviceClient.notifyPatient(email);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void notifyViaText(String phone) {
        log.info("-------Notify via Text---------------");
        try {
            serviceClient.notifyPatient(phone);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String finalizeOnboarding() {
        log.info("-------Completing Onboarding---------------");
        return "yes";
    }

}
