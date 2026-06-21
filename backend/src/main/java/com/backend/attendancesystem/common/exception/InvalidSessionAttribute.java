package com.backend.attendancesystem.common.exception;

public class InvalidSessionAttribute extends RuntimeException {
    public InvalidSessionAttribute(String message) {
        super(message);
    }
}
