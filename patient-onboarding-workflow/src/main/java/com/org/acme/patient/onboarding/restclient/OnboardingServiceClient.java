package com.org.acme.patient.onboarding.restclient;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@FeignClient(name="onboarding-service-client", url = "http://localhost:8090/onboard")
public interface OnboardingServiceClient {

    @PostMapping("/assignhospital/{zip}")
    Hospital assignHospitalToPatient(@PathVariable String zip);

    @PostMapping("/assigndoctor/{condition}")
    Doctor assignDoctorToPatient(@PathVariable String condition);

    @PostMapping("/notify/{email}")
    Patient notifyPatient(@PathVariable String email);
}
