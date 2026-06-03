package com.backend.attendancesystem.schedule.dto;

import com.backend.attendancesystem.enums.WeekDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record ScheduleRequest(
        @NotNull(message = "Institution id cannot be null")
        UUID institutionId,

        @NotNull(message = "Course id cannot be null")
        UUID courseId,

        @NotNull(message = "User id cannot be null")
        UUID userId,

        @NotNull(message = "Day cannot be null")
        WeekDay day,

        @NotNull(message = "From time cannot be null")
        LocalTime fromTime,

        @NotNull(message = "To time cannot be null")
        LocalTime toTime
) {
}
