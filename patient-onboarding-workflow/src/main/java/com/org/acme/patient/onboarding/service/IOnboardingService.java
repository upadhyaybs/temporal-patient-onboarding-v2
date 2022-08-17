package com.org.acme.patient.onboarding.service;

import org.acme.patient.onboarding.model.Patient;

public interface IOnboardingService {

     Patient doOnboard(Patient patient);

     String getStatus(String patientId);
}
