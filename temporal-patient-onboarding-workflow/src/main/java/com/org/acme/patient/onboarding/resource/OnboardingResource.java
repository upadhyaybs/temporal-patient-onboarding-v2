package com.org.acme.patient.onboarding.resource;

import com.org.acme.patient.onboarding.service.IOnboardingService;
import org.acme.patient.onboarding.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/onboard")
public class OnboardingResource {

    @Autowired
    IOnboardingService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public Patient doOnboard(@RequestBody Patient patient){
        return service.doOnboard(patient);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public String getStatus(@RequestParam("id") String patientId){
        return service.getStatus(patientId);
    }

}
