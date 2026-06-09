package com.backend.attendancesystem.common.exception;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handle @Valid errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity.badRequest().body(body);
    }

    //handle invalid enum
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidEnum(
            HttpMessageNotReadableException ex) {

        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of("Invalid request",
                        "Invalid type value")
        );

        return ResponseEntity.badRequest().body(body);
    }

    // Handle entity not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {

        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                List.of("Entity not found", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    //handle data integrity violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of("Data integrity violation")
        );

        return ResponseEntity.badRequest().body(body);
    }

    //handle jdbc exception
    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(JDBCException ex) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of("JDBC Exception")
        );

        return ResponseEntity.badRequest().body(body);
    }

    // Handle invalid institution match
    @ExceptionHandler(InvalidInstitutionException.class)
    public ResponseEntity<ApiError> handleInvalidInstitutionException(InvalidInstitutionException ex) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.getMessage())
        );
        return ResponseEntity.badRequest().body(body);
    }


    // Handle student course enrollment exception
    @ExceptionHandler(StudentCourseException.class)
    public ResponseEntity<ApiError> handleStudentCourseException(StudentCourseException ex) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.getMessage())
        );
        return ResponseEntity.badRequest().body(body);
    }

    // Handle generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {

        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of("Internal server error", ex.getMessage())
        );
        return ResponseEntity.internalServerError().body(body);
    }
}
