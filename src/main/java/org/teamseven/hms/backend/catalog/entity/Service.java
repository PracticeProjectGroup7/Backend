package org.teamseven.hms.backend.catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.teamseven.hms.backend.user.entity.Doctor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE services SET is_active = '0' WHERE servicesid=?")
@Where(clause = "is_active = '1'")
@Table(name = "services")
public class Service {
    @Id
    @Column(name="servicesid", insertable=false)
    @GeneratedValue
    private UUID serviceId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctorid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctorId;

    private String type;

    private String name;

    private String description;

    private String duration;

    private Double estimatedPrice;

    @Column(insertable=false)
    private Integer isActive = 1;

    @Generated()
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
