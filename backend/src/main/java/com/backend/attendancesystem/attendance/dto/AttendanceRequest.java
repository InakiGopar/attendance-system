package com.backend.attendancesystem.attendance.dto;

import com.backend.attendancesystem.enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.UUID;

public record AttendanceRequest(
        UUID studentId,
        UUID courseId,
        UUID institutionId,
        UUID userId,
        LocalDate attendanceDate,
        AttendanceStatus status,
        String observations
) {
}
