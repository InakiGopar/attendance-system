package com.backend.attendancesystem.attendance.mapper;

import com.backend.attendancesystem.attendance.dto.AttendanceRequest;
import com.backend.attendancesystem.attendance.dto.AttendanceResponse;
import com.backend.attendancesystem.attendance.model.AttendanceEntity;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.student.model.StudentEntity;
import com.backend.attendancesystem.user.model.UserEntity;

import java.util.UUID;

public class AttendanceMapper {

    public static AttendanceEntity toEntity(AttendanceRequest request) {
        AttendanceEntity attendance = new AttendanceEntity();

        attendance.setAttendanceId(UUID.randomUUID());
        
        StudentEntity student = new StudentEntity();
        student.setStudentId(request.studentId());
        attendance.setStudent(student);
        
        CourseEntity course = new CourseEntity();
        course.setCourseId(request.courseId());
        attendance.setCourse(course);
        
        InstitutionEntity institution = new InstitutionEntity();
        institution.setInstitutionId(request.institutionId());
        attendance.setInstitution(institution);
        
        UserEntity user = new UserEntity();
        user.setUserId(request.userId());
        attendance.setUser(user);
        
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());
        attendance.setObservations(request.observations());

        return attendance;
    }

    public static AttendanceResponse toResponse(AttendanceEntity entity) {
        return new AttendanceResponse(
                entity.getAttendanceId(),
                entity.getStudent() != null ? entity.getStudent().getStudentId() : null,
                entity.getCourse() != null ? entity.getCourse().getCourseId() : null,
                entity.getInstitution() != null ? entity.getInstitution().getInstitutionId() : null,
                entity.getUser() != null ? entity.getUser().getUserId() : null,
                entity.getAttendanceDate(),
                entity.getStatus(),
                entity.getObservations(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
