package com.backend.attendancesystem.enrollment.dto;

import java.util.UUID;

public record EnrollmentRequest(
        UUID studentId,
        UUID courseId
) {
}
