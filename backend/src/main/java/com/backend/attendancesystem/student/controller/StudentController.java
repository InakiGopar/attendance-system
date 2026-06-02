package com.backend.attendancesystem.student.controller;

import com.backend.attendancesystem.student.dto.StudentRequest;
import com.backend.attendancesystem.student.dto.StudentResponse;
import com.backend.attendancesystem.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> saveStudent(@RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(request));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable UUID studentId,
                                                         @RequestBody StudentRequest request) {
        
        return ResponseEntity.ok(studentService.updateStudent(studentId, request));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentResponse> deleteStudent(@PathVariable UUID studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
