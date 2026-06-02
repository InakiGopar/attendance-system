package com.backend.attendancesystem.user.dto;

import com.backend.attendancesystem.enums.RoleType;

import java.util.UUID;

public record UserRequest(
        UUID institutionId,
        RoleType role,
        String name,
        String lastName,
        String email,
        String password
) {
}
