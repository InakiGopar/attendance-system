package com.backend.attendancesystem.course.dto;

import com.backend.attendancesystem.enums.AttendanceStatus;

import java.util.UUID;

public record AttendanceSheetStudentResponse(
        UUID studentId,
        String name,
        String lastName,
        AttendanceStatus status
) {
}
