package com.backend.attendancesystem.course.mapper;

import com.backend.attendancesystem.course.dto.CourseRequest;
import com.backend.attendancesystem.course.dto.CourseResponse;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.institution.model.InstitutionEntity;

import java.util.UUID;

public class CourseMapper {

    public static CourseEntity toEntity(CourseRequest request) {
        CourseEntity course = new CourseEntity();

        course.setCourseId(UUID.randomUUID());
        
        InstitutionEntity institution = new InstitutionEntity();
        institution.setInstitutionId(request.institutionId());
        course.setInstitution(institution);
        
        course.setName(request.name());

        return course;
    }

    public static CourseResponse toResponse(CourseEntity entity) {
        return new CourseResponse(
                entity.getCourseId(),
                entity.getInstitution() != null ? entity.getInstitution().getInstitutionId() : null,
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
