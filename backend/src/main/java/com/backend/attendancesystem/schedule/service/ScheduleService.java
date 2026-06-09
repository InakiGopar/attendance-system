package com.backend.attendancesystem.schedule.service;

import com.backend.attendancesystem.common.exception.InvalidInstitutionException;
import com.backend.attendancesystem.course.model.CourseEntity;
import com.backend.attendancesystem.course.repository.CourseRepository;
import com.backend.attendancesystem.institution.model.InstitutionEntity;
import com.backend.attendancesystem.institution.repository.InstitutionRepository;
import com.backend.attendancesystem.schedule.dto.ScheduleRequest;
import com.backend.attendancesystem.schedule.dto.ScheduleResponse;
import com.backend.attendancesystem.schedule.mapper.ScheduleMapper;
import com.backend.attendancesystem.schedule.model.ScheduleEntity;
import com.backend.attendancesystem.schedule.repository.ScheduleRepository;
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
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final InstitutionRepository institutionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    @Transactional
    public ScheduleResponse saveSchedule(ScheduleRequest request) {
        //check request
        validateScheduleRequest(request);

        return ScheduleMapper.toResponse(
                scheduleRepository.save(
                        ScheduleMapper.toEntity(request)
                ));
    }

    @Transactional
    public ScheduleResponse updateSchedule(UUID scheduleId, ScheduleRequest request) {
        //check 1
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));

        //check 2
        var validatedEntities = validateScheduleRequest(request);

        schedule.setInstitution(validatedEntities.institution());
        schedule.setCourse(validatedEntities.course());
        schedule.setUser(validatedEntities.user());
        schedule.setDay(request.day());
        schedule.setFromTime(request.fromTime());
        schedule.setToTime(request.toTime());

        return ScheduleMapper.toResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));
        scheduleRepository.deleteById(scheduleId);
    }

    public ScheduleResponse getSchedule(UUID scheduleId) {
         return ScheduleMapper.toResponse(
                 scheduleRepository.findById(scheduleId)
                         .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId))
         );

    }

    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper::toResponse)
                .toList();
    }

    //helper method
    private ValidatedScheduleEntities validateScheduleRequest(ScheduleRequest request) {
        InstitutionEntity institution = institutionRepository.findById(request.institutionId())
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + request.institutionId()));

        CourseEntity course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + request.courseId()));

        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.userId()));

        if (!course.getInstitution().equals(institution) || !user.getInstitution().equals(course.getInstitution())) {
            throw new InvalidInstitutionException("User and course must be from the same institution");
        }

        return new ValidatedScheduleEntities(institution, course, user);
    }

    private record ValidatedScheduleEntities(
            InstitutionEntity institution,
            CourseEntity course,
            UserEntity user
    ) {}
}
