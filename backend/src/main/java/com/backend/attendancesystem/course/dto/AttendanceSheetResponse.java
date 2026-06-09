package com.backend.attendancesystem.course.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AttendanceSheetResponse(
        UUID courseId,
        String courseName,
        LocalDate attendanceDate,
        List<AttendanceSheetStudentResponse> students
) {
}
