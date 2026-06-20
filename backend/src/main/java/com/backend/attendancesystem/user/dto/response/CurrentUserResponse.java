package com.backend.attendancesystem.user.dto.response;

import com.backend.attendancesystem.enums.RoleType;

import java.util.UUID;

public record CurrentUserResponse(
        UUID userId,
        String name,
        String lastName,
        String email,
        UUID institutionId,
        RoleType role
) {
}
