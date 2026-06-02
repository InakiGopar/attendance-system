package com.backend.attendancesystem.enrollment.mapper;

import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.enrollment.dto.EnrollmentRequest;
import com.backend.attendancesystem.enrollment.dto.EnrollmentResponse;
import com.backend.attendancesystem.enrollment.model.EnrollmentEntity;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import com.backend.attendancesystem.student.model.StudentEntity;

public class EnrollmentMapper {

    public static EnrollmentEntity toEntity(EnrollmentRequest request) {
        EnrollmentEntity enrollment = new EnrollmentEntity();

        EnrollmentId id = new EnrollmentId();
        id.setStudentId(request.studentId());
        id.setCourseId(request.courseId());
        enrollment.setId(id);

        StudentEntity student = new StudentEntity();
        student.setStudentId(request.studentId());
        enrollment.setStudent(student);

        CourseEntity course = new CourseEntity();
        course.setCourseId(request.courseId());
        enrollment.setCourse(course);

        return enrollment;
    }

    public static EnrollmentResponse toResponse(EnrollmentEntity entity) {
        return new EnrollmentResponse(
                entity.getStudent() != null ? entity.getStudent().getStudentId() : null,
                entity.getCourse() != null ? entity.getCourse().getCourseId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
