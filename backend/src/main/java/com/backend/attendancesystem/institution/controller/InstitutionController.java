package com.backend.attendancesystem.institution.controller;

import com.backend.attendancesystem.institution.dto.InstitutionRequest;
import com.backend.attendancesystem.institution.dto.InstitutionResponse;
import com.backend.attendancesystem.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/institutions")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<InstitutionResponse> saveInstitution(@RequestBody InstitutionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(institutionService.saveInstitution(request));
    }

    @PutMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> updateInstitution(@PathVariable UUID institutionId,
                                                                 @RequestBody InstitutionRequest request) {
        
        return ResponseEntity.ok(institutionService.updateInstitution(institutionId, request));
    }

    @DeleteMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> deleteInstitution(@PathVariable UUID institutionId) {
        institutionService.deleteInstitution(institutionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> getInstitution(@PathVariable UUID institutionId) {
        return ResponseEntity.ok(institutionService.getInstitution(institutionId));
    }

    @GetMapping
    public ResponseEntity<List<InstitutionResponse>> getInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }
}
