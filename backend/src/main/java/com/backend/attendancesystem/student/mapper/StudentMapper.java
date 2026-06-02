package com.backend.attendancesystem.student.mapper;

import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.student.dto.StudentRequest;
import com.backend.attendancesystem.student.dto.StudentResponse;
import com.backend.attendancesystem.student.model.StudentEntity;

import java.util.UUID;

public class StudentMapper {

    public static StudentEntity toEntity(StudentRequest request) {
        StudentEntity student = new StudentEntity();

        student.setStudentId(UUID.randomUUID());
        
        InstitutionEntity institution = new InstitutionEntity();
        institution.setInstitutionId(request.institutionId());
        student.setInstitution(institution);
        
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setBirthDate(request.birthDate());
        student.setNationality(request.nationality());

        return student;
    }

    public static StudentResponse toResponse(StudentEntity entity) {
        return new StudentResponse(
                entity.getStudentId(),
                entity.getInstitution() != null ? entity.getInstitution().getInstitutionId() : null,
                entity.getName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getNationality(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
