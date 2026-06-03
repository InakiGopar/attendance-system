package com.backend.attendancesystem.attendance.service;

import com.backend.attendancesystem.attendance.dto.AttendanceRequest;
import com.backend.attendancesystem.attendance.dto.AttendanceResponse;
import com.backend.attendancesystem.attendance.mapper.AttendanceMapper;
import com.backend.attendancesystem.attendance.model.AttendanceEntity;
import com.backend.attendancesystem.attendance.repository.AttendanceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public AttendanceResponse saveAttendance(AttendanceRequest request) {
        return AttendanceMapper.toResponse(
                attendanceRepository.save(
                        AttendanceMapper.toEntity(request)
                ));
    }

    @Transactional
    public AttendanceResponse updateAttendance(UUID attendanceId, AttendanceRequest request) {
        AttendanceEntity attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId));
        
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());
        attendance.setObservations(request.observations());

        return AttendanceMapper.toResponse(attendance);
    }

    @Transactional
    public void deleteAttendance(UUID attendanceId) {
        attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId));
        attendanceRepository.deleteById(attendanceId);
    }

    public AttendanceResponse getAttendance(UUID attendanceId) {
         return AttendanceMapper.toResponse(
                 attendanceRepository.findById(attendanceId)
                         .orElseThrow(() -> new EntityNotFoundException("Attendance not found with the id: " + attendanceId))
         );

    }

    public List<AttendanceResponse> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }
}
