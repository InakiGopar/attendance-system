package com.backend.attendancesystem.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CourseRequest(
        @NotNull(message = "Institution id cannot be null")
        UUID institutionId,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name
) {
}
