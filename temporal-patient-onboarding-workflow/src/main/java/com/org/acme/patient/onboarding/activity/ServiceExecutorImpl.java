package com.org.acme.patient.onboarding.activity;

import com.org.acme.patient.onboarding.restclient.OnboardingServiceClient;
import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;

public class ServiceExecutorImpl implements ServiceExecutor{

    private OnboardingServiceClient serviceClient;

    public ServiceExecutorImpl (OnboardingServiceClient serviceClient){
        this.serviceClient=serviceClient;
    }

    @Override
    public Hospital assignHospitalToPatient(String zip) {
        System.out.print("-------Assign Hospital---------------");
        // call onboarding service
        Hospital hospital = serviceClient.assignHospitalToPatient(zip);
        // simulate some work...
        sleep(5);
        return hospital;
    }

    @Override
    public Doctor assignDoctorToPatient(String condition) {
        System.out.print("-------Assign Doctor---------------");
        Doctor doctor = serviceClient.assignDoctorToPatient(condition);
        // simulate some work...
        sleep(5);
        return doctor;
    }

    @Override
    public void notifyViaEmail(String email) {
        System.out.print("-------Notify via email---------------");
        try {
            serviceClient.notifyPatient(email);
            // simulate some work...
            sleep(5);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void notifyViaText(String phone) {
        System.out.print("-------Notify via Text---------------");
        try {
            serviceClient.notifyPatient(phone);
            // simulate some work...
            sleep(5);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String finalizeOnboarding() {
        System.out.print("-------Completing Onboarding---------------");
        // simulate some work...
        sleep(5);
        return "yes";
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ee) {
            // Empty
        }
    }
}
