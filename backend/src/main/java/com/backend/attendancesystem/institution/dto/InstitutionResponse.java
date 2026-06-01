package com.backend.attendancesystem.institution.dto;

import java.util.UUID;

public record InstitutionResponse(UUID institutionId, String name) {
}
