package com.backend.attendancesystem.enrollment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnrollmentResponse(
        UUID studentId,
        UUID courseId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
