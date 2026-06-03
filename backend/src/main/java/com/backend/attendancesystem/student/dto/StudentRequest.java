package com.backend.attendancesystem.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record StudentRequest(
        @NotNull(message = "Institution id cannot be null")
        UUID institutionId,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name,

        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 255, message = "Last name cannot exceed 255 characters")
        String lastName,

        @NotNull(message = "Birth date cannot be null")
        @PastOrPresent(message = "Birth date cannot be in the future")
        LocalDate birthDate,

        @Size(max = 255, message = "Nationality cannot exceed 255 characters")
        String nationality
) {
}
