package com.backend.attendancesystem.schedule.mapper;

import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.schedule.dto.ScheduleRequest;
import com.backend.attendancesystem.schedule.dto.ScheduleResponse;
import com.backend.attendancesystem.schedule.model.ScheduleEntity;
import com.backend.attendancesystem.user.model.UserEntity;

import java.util.UUID;

public class ScheduleMapper {

    public static ScheduleEntity toEntity(ScheduleRequest request) {
        ScheduleEntity schedule = new ScheduleEntity();

        schedule.setScheduleId(UUID.randomUUID());
        
        InstitutionEntity institution = new InstitutionEntity();
        institution.setInstitutionId(request.institutionId());
        schedule.setInstitution(institution);
        
        CourseEntity course = new CourseEntity();
        course.setCourseId(request.courseId());
        schedule.setCourse(course);
        
        UserEntity user = new UserEntity();
        user.setUserId(request.userId());
        schedule.setUser(user);
        
        schedule.setDay(request.day());
        schedule.setFromTime(request.fromTime());
        schedule.setToTime(request.toTime());

        return schedule;
    }

    public static ScheduleResponse toResponse(ScheduleEntity entity) {
        return new ScheduleResponse(
                entity.getScheduleId(),
                entity.getInstitution() != null ? entity.getInstitution().getInstitutionId() : null,
                entity.getCourse() != null ? entity.getCourse().getCourseId() : null,
                entity.getUser() != null ? entity.getUser().getUserId() : null,
                entity.getDay(),
                entity.getFromTime(),
                entity.getToTime(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
