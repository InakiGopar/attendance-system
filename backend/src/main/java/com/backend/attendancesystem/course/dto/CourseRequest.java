package com.backend.attendancesystem.course.dto;

import java.util.UUID;

public record CourseRequest(
        UUID institutionId,
        String name
) {
}
