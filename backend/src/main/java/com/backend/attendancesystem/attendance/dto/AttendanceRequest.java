package com.backend.attendancesystem.attendance.dto;

import com.backend.attendancesystem.enums.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AttendanceRequest(
        @NotNull(message = "Student id cannot be null")
        UUID studentId,

        @NotNull(message = "Course id cannot be null")
        UUID courseId,

        @NotNull(message = "Institution id cannot be null")
        UUID institutionId,

        @NotNull(message = "User id cannot be null")
        UUID userId,

        @NotNull(message = "Attendance date cannot be null")
        @PastOrPresent(message = "Attendance date cannot be in the future")
        LocalDate attendanceDate,

        @NotNull(message = "Status cannot be null")
        AttendanceStatus status,

        @Size(max = 255, message = "Observations cannot exceed 255 characters")
        String observations
) {
}
