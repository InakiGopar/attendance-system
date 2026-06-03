package com.backend.attendancesystem.enrollment.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentRequest(
        @NotNull(message = "Student id cannot be null")
        UUID studentId,

        @NotNull(message = "Course id cannot be null")
        UUID courseId
) {
}
