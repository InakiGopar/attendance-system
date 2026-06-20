package com.backend.attendancesystem.user.mapper;

import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.user.dto.request.UserRequest;
import com.backend.attendancesystem.user.dto.response.UserResponse;
import com.backend.attendancesystem.user.model.UserEntity;

import java.util.UUID;

public class UserMapper {

    public static UserEntity toEntity(UserRequest request) {
        UserEntity user = new UserEntity();

        user.setUserId(UUID.randomUUID());
        
        InstitutionEntity institution = new InstitutionEntity();
        institution.setInstitutionId(request.institutionId());
        user.setInstitution(institution);
        
        user.setRole(request.role());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());

        return user;
    }

    public static UserResponse toResponse(UserEntity entity) {
        return new UserResponse(
                entity.getUserId(),
                entity.getInstitution() != null ? entity.getInstitution().getInstitutionId() : null,
                entity.getRole(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
