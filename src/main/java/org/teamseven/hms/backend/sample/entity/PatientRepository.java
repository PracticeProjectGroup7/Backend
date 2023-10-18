package org.teamseven.hms.backend.sample.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends CrudRepository<Patient, UUID> {
    // custom query support https://docs.spring.io/spring-data/jpa/docs/1.11.1.RELEASE/reference/html/#jpa.query-methods.query-creation
    public List<Patient> findByBloodType(BloodType bloodType);
}