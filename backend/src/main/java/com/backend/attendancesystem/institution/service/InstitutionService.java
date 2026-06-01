package com.backend.attendancesystem.institution.service;

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
    public InstitutionEntity saveInstitution(InstitutionEntity institution) {
        return institutionRepository.save(institution);
    }

    @Transactional
    public InstitutionEntity updateInstitution(UUID institutionId, InstitutionEntity request) {
        //todo: add exception handling
        InstitutionEntity institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
        institution.setName(request.getName());
        return institution;
    }

    @Transactional
    public void deleteInstitution(UUID institutionId) {
        //todo: add exception handling
        institutionRepository.deleteById(institutionId);
    }

    public InstitutionEntity getInstitution(UUID institutionId) {
        //todo: add exception handling
        return institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
    }

    public List<InstitutionEntity> getAllInstitutions() {
        return institutionRepository.findAll();
    }


}
