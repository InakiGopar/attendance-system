package com.backend.attendancesystem.institution.service;

import com.backend.attendancesystem.institution.dto.InstitutionRequest;
import com.backend.attendancesystem.institution.dto.InstitutionResponse;
import com.backend.attendancesystem.institution.mapper.InstitutionMapper;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
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
        InstitutionEntity institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
        institution.setName(request.name());

        return InstitutionMapper.toResponse(institution);
    }

    @Transactional
    public void deleteInstitution(UUID institutionId) {
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
        institutionRepository.deleteById(institutionId);
    }

    public InstitutionResponse getInstitution(UUID institutionId) {
         return InstitutionMapper.toResponse(
                 institutionRepository.findById(institutionId)
                         .orElseThrow(() -> new EntityNotFoundException("Institution not found with id:" + institutionId))
         );

    }

    public List<InstitutionResponse> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(InstitutionMapper::toResponse)
                .toList();
    }


}
