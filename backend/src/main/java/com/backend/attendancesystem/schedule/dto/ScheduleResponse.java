package com.backend.attendancesystem.schedule.dto;

import com.backend.attendancesystem.enums.WeekDay;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleResponse(
        UUID scheduleId,
        UUID institutionId,
        UUID courseId,
        UUID userId,
        WeekDay day,
        LocalTime fromTime,
        LocalTime toTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
