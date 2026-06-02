package com.backend.attendancesystem.schedule.service;

import com.backend.attendancesystem.schedule.dto.ScheduleRequest;
import com.backend.attendancesystem.schedule.dto.ScheduleResponse;
import com.backend.attendancesystem.schedule.mapper.ScheduleMapper;
import com.backend.attendancesystem.schedule.model.ScheduleEntity;
import com.backend.attendancesystem.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse saveSchedule(ScheduleRequest request) {
        return ScheduleMapper.toResponse(
                scheduleRepository.save(
                        ScheduleMapper.toEntity(request)
                ));
    }

    @Transactional
    public ScheduleResponse updateSchedule(UUID scheduleId, ScheduleRequest request) {
        //todo: add exception handling
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        
        schedule.setDay(request.day());
        schedule.setFromTime(request.fromTime());
        schedule.setToTime(request.toTime());

        return ScheduleMapper.toResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        //todo: add exception handling
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        scheduleRepository.deleteById(scheduleId);
    }

    public ScheduleResponse getSchedule(UUID scheduleId) {
        //todo: add exception handling
         return ScheduleMapper.toResponse(
                 scheduleRepository.findById(scheduleId)
                         .orElseThrow(() -> new RuntimeException("Schedule not found"))
         );

    }

    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper::toResponse)
                .toList();
    }
}
