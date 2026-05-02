package com.general.nitin.springjpa.controller;

import com.general.nitin.springjpa.dto.response.ApiResponse;
import com.general.nitin.springjpa.dto.request.StudentRequest;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // Autowired, Constructor Based Injection, Setter Based Injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create Student with relationships
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentRequest request) {
        // validation
        Student student = studentService.createStudent(
                request.getStudent(),
                request.getDepartmentId(),
                request.getCourseIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(HttpStatus.CREATED.value(), student));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), studentService.getStudent(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), "Student deleted successfully"));
    }
}