package com.backend.attendancesystem.course.service;

import com.backend.attendancesystem.course.dto.CourseRequest;
import com.backend.attendancesystem.course.dto.CourseResponse;
import com.backend.attendancesystem.course.mapper.CourseMapper;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.course.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public CourseResponse saveCourse(CourseRequest request) {
        return CourseMapper.toResponse(
                courseRepository.save(
                        CourseMapper.toEntity(request)
                ));
    }

    @Transactional
    public CourseResponse updateCourse(UUID courseId, CourseRequest request) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with the id: " + courseId));
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
}
