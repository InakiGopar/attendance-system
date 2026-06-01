package com.backend.attendancesystem.institution.mapper;

import com.backend.attendancesystem.institution.dto.InstitutionRequest;
import com.backend.attendancesystem.institution.dto.InstitutionResponse;
import com.backend.attendancesystem.institution.model.InstitutionEntity;

import java.util.UUID;

public class InstitutionMapper {

    public static InstitutionEntity toEntity(InstitutionRequest request) {
        InstitutionEntity institution = new InstitutionEntity();

        institution.setInstitutionId(UUID.randomUUID());
        institution.setName(request.name());

        return institution;
    }

    public static InstitutionResponse toResponse(InstitutionEntity entity) {
        return new InstitutionResponse(
                entity.getInstitutionId(),
                entity.getName()
        );
    }
}
