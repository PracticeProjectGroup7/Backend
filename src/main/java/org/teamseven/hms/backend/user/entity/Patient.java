package org.teamseven.hms.backend.user.entity;

import jakarta.persistence.*;
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
@SQLDelete(sql = "UPDATE patient SET is_active = 0 WHERE patientid=?")
@Where(clause = "is_active = 1")
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name="patientid", insertable=false)
    @GeneratedValue
    private UUID patientId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "bloodgroup")
    private String bloodGroup;

    @Column(name = "medicalcondition")
    private String medicalCondition;

    @Column(insertable=false)
    private Integer isActive = 1;

    @Generated
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
