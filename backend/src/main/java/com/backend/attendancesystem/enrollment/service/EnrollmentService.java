package com.backend.attendancesystem.enrollment.service;

import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.course.repository.CourseRepository;
import com.backend.attendancesystem.enrollment.dto.EnrollmentRequest;
import com.backend.attendancesystem.enrollment.dto.EnrollmentResponse;
import com.backend.attendancesystem.enrollment.mapper.EnrollmentMapper;
import com.backend.attendancesystem.enrollment.model.EnrollmentEntity;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import com.backend.attendancesystem.enrollment.repository.EnrollmentRepository;
import com.backend.attendancesystem.student.model.StudentEntity;
import com.backend.attendancesystem.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public EnrollmentResponse saveEnrollment(EnrollmentRequest request) {
        //check 1
        StudentEntity student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + request.studentId()));
        //check 2
        CourseEntity course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + request.courseId()));
        //check 3
        if (!student.getInstitution().equals(course.getInstitution())) {
            //todo: add domain exception
            throw new RuntimeException("Student and course must be from the same institution");
        }

        return EnrollmentMapper.toResponse(
                enrollmentRepository.save(
                        EnrollmentMapper.toEntity(request)
                ));
    }


    @Transactional
    public void deleteEnrollment(UUID studentId, UUID courseId) {

        //check 1
        studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        //check 2
        courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        EnrollmentId id = new EnrollmentId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);
        //check 3
        enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( "Enrollment not found for student "
                        + studentId +
                        " and course "
                        + courseId));

        enrollmentRepository.deleteById(id);
    }

    public EnrollmentResponse getEnrollment(UUID studentId, UUID courseId) {

        //check 1
        studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        //check 2
        courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        EnrollmentId id = new EnrollmentId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);
        
        return EnrollmentMapper.toResponse(
                enrollmentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException( "Enrollment not found for student "
                                + studentId +
                                " and course "
                                + courseId))
        );
    }

    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }
}
