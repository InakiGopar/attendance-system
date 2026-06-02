package com.backend.attendancesystem.user.dto;

import com.backend.attendancesystem.enums.RoleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        UUID institutionId,
        RoleType role,
        String name,
        String lastName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
