package com.backend.attendancesystem.auth.dto;

public record GoogleUserInfo(
        String email,
        String name,
        String lastName
) {
}
