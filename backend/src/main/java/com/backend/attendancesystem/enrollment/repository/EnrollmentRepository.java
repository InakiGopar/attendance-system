package com.backend.attendancesystem.enrollment.repository;

import com.backend.attendancesystem.course.dto.AttendanceSheetStudentResponse;
import com.backend.attendancesystem.enrollment.model.EnrollmentEntity;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, EnrollmentId> {

    @Query("""
    SELECT new com.backend.attendancesystem.course.dto.AttendanceSheetStudentResponse(
        s.studentId,
        s.name,
        s.lastName,
        null
    )
    FROM EnrollmentEntity e
        JOIN e.student s
    WHERE e.course.courseId = :courseId
    ORDER BY s.lastName, s.name
""")
    List<AttendanceSheetStudentResponse> findStudentsByCourseId(
            UUID courseId
    );
}
