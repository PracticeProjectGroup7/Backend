package org.teamseven.hms.backend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.shared.ResponseWrapper;
import org.teamseven.hms.backend.user.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/{patientId}")
    public ResponseEntity<ResponseWrapper> getPatientProfile(
            @PathVariable UUID patientId
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(patientService.getPatientProfile(patientId))
        );
    }

    @PostMapping("/info")
    public ResponseEntity<ResponseWrapper> getPatientByIds(
            @RequestBody List<UUID> uuids
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(patientService.getByUUIDs(uuids))
        );
    }
}
