package com.backend.attendancesystem.student.dto;

import java.time.LocalDate;
import java.util.UUID;

public record StudentRequest(
        UUID institutionId,
        String name,
        String lastName,
        LocalDate birthDate,
        String nationality
) {
}
