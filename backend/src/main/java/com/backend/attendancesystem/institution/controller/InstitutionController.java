package com.backend.attendancesystem.institution.controller;

import com.backend.attendancesystem.institution.dto.InstitutionRequest;
import com.backend.attendancesystem.institution.dto.InstitutionResponse;
import com.backend.attendancesystem.institution.mapper.InstitutionMapper;
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
    public ResponseEntity<InstitutionResponse> saveInstitution(@RequestBody InstitutionRequest institutionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                InstitutionMapper.toResponse(
                    institutionService.saveInstitution(
                        InstitutionMapper.toEntity(institutionRequest)
                    )
                )
            );
    }

    @PutMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> updateInstitution(@PathVariable UUID institutionId,
                                                                 @RequestBody InstitutionRequest institutionRequest) {
        return ResponseEntity.ok(InstitutionMapper.toResponse(
                institutionService.updateInstitution(
                    institutionId,
                    InstitutionMapper.toEntity(institutionRequest)
                )
        ));
    }

    @DeleteMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> deleteInstitution(@PathVariable UUID institutionId) {
        institutionService.deleteInstitution(institutionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> getInstitution(@PathVariable UUID institutionId) {
        return ResponseEntity.ok(
                InstitutionMapper.toResponse(
                    institutionService.getInstitution(institutionId)
                )
        );
    }

    @GetMapping
    public  ResponseEntity<List<InstitutionResponse>> getInstitutions() {
        return ResponseEntity.ok(
                institutionService.getAllInstitutions().stream()
                    .map(InstitutionMapper::toResponse)
                    .toList()
        );
    }
}
