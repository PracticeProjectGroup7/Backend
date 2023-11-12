package org.teamseven.hms.backend.admin.annotation;

public enum PatientDataRequestedMethod {

    PATIENT_ID_PATH("patientId"),
    BOOKING_ID_PATH("bookingId");

    private String variableName;
    PatientDataRequestedMethod(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
