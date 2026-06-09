package com.backend.attendancesystem.course.service;

import com.backend.attendancesystem.course.dto.AttendanceSheetResponse;
import com.backend.attendancesystem.course.dto.AttendanceSheetStudentResponse;
import com.backend.attendancesystem.course.dto.CourseRequest;
import com.backend.attendancesystem.course.dto.CourseResponse;
import com.backend.attendancesystem.course.mapper.CourseMapper;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.course.repository.CourseRepository;
import com.backend.attendancesystem.enrollment.repository.EnrollmentRepository;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstitutionRepository institutionRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public CourseResponse saveCourse(CourseRequest request) {
        //check request
        validateInstitutionExists(request.institutionId());

        return CourseMapper.toResponse(
                courseRepository.save(
                        CourseMapper.toEntity(request)
                ));
    }

    @Transactional
    public CourseResponse updateCourse(UUID courseId, CourseRequest request) {
        //check 1
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with the id: " + courseId));

        //check 2
        validateInstitutionExists(request.institutionId());

        course.setName(request.name());

        return CourseMapper.toResponse(course);
    }

    @Transactional
    public void deleteCourse(UUID courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with the id: " + courseId));
        courseRepository.deleteById(courseId);
    }

    public CourseResponse getCourse(UUID courseId) {
         return CourseMapper.toResponse(
                 courseRepository.findById(courseId)
                         .orElseThrow(() -> new EntityNotFoundException("Course not found with the id: " + courseId))
         );

    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    public AttendanceSheetResponse getAttendanceSheet(UUID courseId) {

        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Course not found with id: " + courseId
                        ));

        List<AttendanceSheetStudentResponse> students =
                enrollmentRepository.findStudentsByCourseId(courseId);

        return new AttendanceSheetResponse(
                course.getCourseId(),
                course.getName(),
                LocalDate.now(),
                students
        );
    }

    //helper method
    private void validateInstitutionExists(UUID institutionId) {
        institutionRepository.findById(institutionId)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + institutionId));
    }
}
