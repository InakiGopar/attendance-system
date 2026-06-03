package com.backend.attendancesystem.common.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        List<String> details
) {
}
