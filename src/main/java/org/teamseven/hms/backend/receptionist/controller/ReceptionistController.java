package org.teamseven.hms.backend.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.admin.service.AdminService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/recep")
@RequiredArgsConstructor
public class ReceptionistController {
    @Autowired private AdminService adminService;

    @GetMapping("/patients")
    public ResponseEntity<ResponseWrapper> getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.getAllPatients(page, pageSize)));
    }
}
