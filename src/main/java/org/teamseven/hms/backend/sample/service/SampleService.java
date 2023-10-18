package org.teamseven.hms.backend.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.sample.dto.GetPatientInfoResponse;
import org.teamseven.hms.backend.sample.dto.SampleResponse;
import org.teamseven.hms.backend.sample.entity.*;
import org.teamseven.hms.backend.shared.exception.SampleCustomFieldUnexpectedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SampleService {

    @Autowired
    PatientRepository patientRepository;
    public SampleResponse getSampleResponse() {
        return new SampleResponse(1, "test");
    }

    public SampleResponse getSampleResponseNonCustomException() {
        throw new IllegalArgumentException("test exception");
    }

    public SampleResponse getSampleResponseCustomException() throws SampleCustomFieldUnexpectedException {
        throw new SampleCustomFieldUnexpectedException(
                "test custom exception",
                new String[]{"test_error_field"}
        );
    }

    public GetPatientInfoResponse getSamplePatientInfo(UUID patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isEmpty()) {
            throw new RuntimeException("Data not found");
        }

        return convertPatientToPatientInfo(patient.get());
    }

    public List<GetPatientInfoResponse> getPatientsByBloodType(BloodType bloodType) {
        List<Patient> patient = patientRepository.findByBloodType(bloodType);

        return patient
                .stream()
                .map(this::convertPatientToPatientInfo)
                .collect(Collectors.toList());
    }

    private GetPatientInfoResponse convertPatientToPatientInfo(Patient patient) {
        User user = patient.getUser();

        return new GetPatientInfoResponse(
                patient.getId(),
                patient.getBloodType(),
                patient.getMedicalCondition(),
                user.getPhoneNumber(),
                user.getGender().name(),
                user.getDateOfBirth()
        );
    }
}
