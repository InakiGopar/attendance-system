package com.backend.attendancesystem.attendance.service;

import com.backend.attendancesystem.attendance.dto.AttendanceRequest;
import com.backend.attendancesystem.attendance.dto.AttendanceResponse;
import com.backend.attendancesystem.attendance.mapper.AttendanceMapper;
import com.backend.attendancesystem.attendance.model.AttendanceEntity;
import com.backend.attendancesystem.attendance.repository.AttendanceRepository;
import com.backend.attendancesystem.common.exception.InvalidInstitutionException;
import com.backend.attendancesystem.common.exception.StudentCourseException;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.course.repository.CourseRepository;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import com.backend.attendancesystem.enrollment.repository.EnrollmentRepository;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import com.backend.attendancesystem.student.model.StudentEntity;
import com.backend.attendancesystem.student.repository.StudentRepository;
import com.backend.attendancesystem.user.model.UserEntity;
import com.backend.attendancesystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public AttendanceResponse saveAttendance(AttendanceRequest request) {
        //check request
        validateAttendanceRequest(request);

        return AttendanceMapper.toResponse(
                attendanceRepository.save(
                        AttendanceMapper.toEntity(request)
                ));
    }

    @Transactional
    public AttendanceResponse updateAttendance(UUID attendanceId, AttendanceRequest request) {
        //check 1
        AttendanceEntity attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId));

        //check request
        var validatedAttendance = validateAttendanceRequest(request);

        attendance.setStudent(validatedAttendance.student);
        attendance.setCourse(validatedAttendance.course);
        attendance.setInstitution(validatedAttendance.institution);
        attendance.setUser(validatedAttendance.user);
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());
        attendance.setObservations(request.observations());

        return AttendanceMapper.toResponse(attendance);
    }

    @Transactional
    public void deleteAttendance(UUID attendanceId) {
        attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId));
        attendanceRepository.deleteById(attendanceId);
    }

    public AttendanceResponse getAttendance(UUID attendanceId) {
         return AttendanceMapper.toResponse(
                 attendanceRepository.findById(attendanceId)
                         .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId))
         );

    }

    public List<AttendanceResponse> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }

    // helper method
    private ValidatedAttendanceEntities validateAttendanceRequest(AttendanceRequest request) {
        StudentEntity student = studentRepository.findById(request.studentId()).orElseThrow(() ->
                new EntityNotFoundException("Student not found with id: " + request.studentId()));

        CourseEntity course = courseRepository.findById(request.courseId()).orElseThrow(() ->
                new EntityNotFoundException("Course not found with id: " + request.courseId()));

        InstitutionEntity institution = institutionRepository.findById(request.institutionId()).orElseThrow(() ->
                new EntityNotFoundException("Institution not found with id: " + request.institutionId()));

        UserEntity user = userRepository.findById(request.userId()).orElseThrow(() ->
                new EntityNotFoundException("User not found with id: " + request.userId()));


        if (!isValidInstitutionRule(institution, student, course, user)) {
            throw new InvalidInstitutionException("Student, course and user must match in the same institution");
        }


        if (!isStudentEnrolledInCourse(course.getCourseId(), student.getStudentId())) {
            throw new StudentCourseException("Student is not enrolled in the course");
        }

        return new ValidatedAttendanceEntities(student, course, institution, user);
    }

    // helper method
    private boolean isValidInstitutionRule(InstitutionEntity institution, StudentEntity student, CourseEntity course, UserEntity user) {
        return institution.equals(student.getInstitution())
                && institution.equals(course.getInstitution())
                && institution.equals(user.getInstitution());
    }

    // helper method
    private boolean isStudentEnrolledInCourse(UUID courseId, UUID studentId) {
        EnrollmentId enrollmentId = new EnrollmentId();
        enrollmentId.setCourseId(courseId);
        enrollmentId.setStudentId(studentId);

        return enrollmentRepository.existsById(enrollmentId);
    }

    private record ValidatedAttendanceEntities(
            StudentEntity student,
            CourseEntity course,
            InstitutionEntity institution,
            UserEntity user
    ) {}
}
