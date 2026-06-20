package com.backend.attendancesystem.user.dto.response;

import java.time.LocalTime;
import java.util.UUID;

public record UserCourseResponse(
        UUID courseId,
        String courseName,
        LocalTime fromTime,
        LocalTime toTime
) {
}
