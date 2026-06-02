package com.backend.attendancesystem.enrollment.service;

import com.backend.attendancesystem.enrollment.dto.EnrollmentRequest;
import com.backend.attendancesystem.enrollment.dto.EnrollmentResponse;
import com.backend.attendancesystem.enrollment.mapper.EnrollmentMapper;
import com.backend.attendancesystem.enrollment.model.EnrollmentEntity;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import com.backend.attendancesystem.enrollment.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public EnrollmentResponse saveEnrollment(EnrollmentRequest request) {
        return EnrollmentMapper.toResponse(
                enrollmentRepository.save(
                        EnrollmentMapper.toEntity(request)
                ));
    }


    @Transactional
    public void deleteEnrollment(UUID studentId, UUID courseId) {
        //todo: add exception handling
        EnrollmentId id = new EnrollmentId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);
        
        enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollmentRepository.deleteById(id);
    }

    public EnrollmentResponse getEnrollment(UUID studentId, UUID courseId) {
        //todo: add exception handling
        EnrollmentId id = new EnrollmentId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);
        
        return EnrollmentMapper.toResponse(
                enrollmentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Enrollment not found"))
        );
    }

    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }
}
