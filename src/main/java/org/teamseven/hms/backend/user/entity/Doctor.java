package org.teamseven.hms.backend.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE doctor SET is_active = 0 WHERE servicesid=?")
@Where(clause = "is_active = 1")
@Table(name = "doctor")
public class Doctor {
    @Id
    @Column(name="doctorid", insertable=false)
    @GeneratedValue
    private UUID doctorId;

    @Column(name = "userid")
    private String userId;

    private String speciality;

    @Column(name = "consultationfees")
    private Double consultationFees;

    @Column(name = "yearsofexp")
    private Integer yearsOfExperience;

    @NotNull
    @Column(insertable=false)
    private Integer isActive = 1;

    @Generated()
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
