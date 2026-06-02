package com.backend.attendancesystem.institution.service;

import com.backend.attendancesystem.institution.dto.InstitutionRequest;
import com.backend.attendancesystem.institution.dto.InstitutionResponse;
import com.backend.attendancesystem.institution.mapper.InstitutionMapper;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    @Transactional
    public InstitutionResponse saveInstitution(InstitutionRequest request) {
        return InstitutionMapper.toResponse(
                institutionRepository.save(
                        InstitutionMapper.toEntity(request)
                ));
    }

    @Transactional
    public InstitutionResponse updateInstitution(UUID institutionId, InstitutionRequest request) {
        //todo: add exception handling
        InstitutionEntity institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
        institution.setName(request.name());

        return InstitutionMapper.toResponse(institution);
    }

    @Transactional
    public void deleteInstitution(UUID institutionId) {
        //todo: add exception handling
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
        institutionRepository.deleteById(institutionId);
    }

    public InstitutionResponse getInstitution(UUID institutionId) {
        //todo: add exception handling
         return InstitutionMapper.toResponse(
                 institutionRepository.findById(institutionId)
                         .orElseThrow(() -> new RuntimeException("Institution not found"))
         );

    }

    public List<InstitutionResponse> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(InstitutionMapper::toResponse)
                .toList();
    }


}
