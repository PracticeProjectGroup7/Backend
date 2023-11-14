package org.teamseven.hms.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String nric;
    private String address;
    private String dateOfBirth;
    private String type;
    private String gender;
    private String bloodGroup;
    private String medicalCondition;
    private String specialty;
    private Role role;
    private Double consultationFees;
    private Integer yearsOfExperience;
}
