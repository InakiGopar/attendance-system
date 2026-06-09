package com.backend.attendancesystem.student.service;

import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import com.backend.attendancesystem.student.dto.StudentRequest;
import com.backend.attendancesystem.student.dto.StudentResponse;
import com.backend.attendancesystem.student.mapper.StudentMapper;
import com.backend.attendancesystem.student.model.StudentEntity;
import com.backend.attendancesystem.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final InstitutionRepository institutionRepository;

    @Transactional
    public StudentResponse saveStudent(StudentRequest request) {
        //check request
        validateInstitutionExists(request.institutionId());

        return StudentMapper.toResponse(
                studentRepository.save(
                        StudentMapper.toEntity(request)
                ));
    }

    @Transactional
    public StudentResponse updateStudent(UUID studentId, StudentRequest request) {
        //check 1
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        //check 2
        validateInstitutionExists(request.institutionId());

        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setBirthDate(request.birthDate());
        student.setNationality(request.nationality());

        return StudentMapper.toResponse(student);
    }

    @Transactional
    public void deleteStudent(UUID studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        studentRepository.deleteById(studentId);
    }

    public StudentResponse getStudent(UUID studentId) {
         return StudentMapper.toResponse(
                 studentRepository.findById(studentId)
                         .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId))
         );

    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    private void validateInstitutionExists(UUID institutionId) {
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
    }
}
