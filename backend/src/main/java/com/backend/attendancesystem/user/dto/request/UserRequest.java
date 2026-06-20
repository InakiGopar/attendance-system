package com.backend.attendancesystem.user.dto.request;

import com.backend.attendancesystem.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRequest(
        @NotNull(message = "Institution id cannot be null")
        UUID institutionId,

        @NotNull(message = "Role cannot be null")
        RoleType role,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name,

        @NotNull(message = "Last name cannot be null")
        @Size(max = 255, message = "Last name cannot exceed 255 characters")
        String lastName,

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

        @NotNull(message = "Password cannot be null")
        @Size(max = 255, message = "Password cannot exceed 255 characters")
        String password
) {
}
