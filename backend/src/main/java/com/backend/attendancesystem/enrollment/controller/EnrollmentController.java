package com.backend.attendancesystem.enrollment.controller;

import com.backend.attendancesystem.enrollment.dto.EnrollmentRequest;
import com.backend.attendancesystem.enrollment.dto.EnrollmentResponse;
import com.backend.attendancesystem.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> saveEnrollment(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.saveEnrollment(request));
    }


    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentResponse> deleteEnrollment(@PathVariable UUID studentId,
                                                             @PathVariable UUID courseId) {
        enrollmentService.deleteEnrollment(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentResponse> getEnrollment(@PathVariable UUID studentId,
                                                          @PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollment(studentId, courseId));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }
}
