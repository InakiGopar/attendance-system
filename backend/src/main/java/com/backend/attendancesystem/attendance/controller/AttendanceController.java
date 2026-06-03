package com.backend.attendancesystem.attendance.controller;

import com.backend.attendancesystem.attendance.dto.AttendanceRequest;
import com.backend.attendancesystem.attendance.dto.AttendanceResponse;
import com.backend.attendancesystem.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponse> saveAttendance(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.saveAttendance(request));
    }

    @PutMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponse> updateAttendance(@PathVariable UUID attendanceId,
                                                                 @Valid @RequestBody AttendanceRequest request) {
        
        return ResponseEntity.ok(attendanceService.updateAttendance(attendanceId, request));
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponse> deleteAttendance(@PathVariable UUID attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponse> getAttendance(@PathVariable UUID attendanceId) {
        return ResponseEntity.ok(attendanceService.getAttendance(attendanceId));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }
}
