package com.backend.attendancesystem.course.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CourseResponse(
        UUID courseId,
        UUID institutionId,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
