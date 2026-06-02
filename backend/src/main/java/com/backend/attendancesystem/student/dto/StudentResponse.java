package com.backend.attendancesystem.student.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record StudentResponse(
        UUID studentId,
        UUID institutionId,
        String name,
        String lastName,
        LocalDate birthDate,
        String nationality,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
