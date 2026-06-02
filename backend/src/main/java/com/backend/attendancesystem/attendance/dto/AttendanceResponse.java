package com.backend.attendancesystem.attendance.dto;

import com.backend.attendancesystem.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceResponse(
        UUID attendanceId,
        UUID studentId,
        UUID courseId,
        UUID institutionId,
        UUID userId,
        LocalDate attendanceDate,
        AttendanceStatus status,
        String observations,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
