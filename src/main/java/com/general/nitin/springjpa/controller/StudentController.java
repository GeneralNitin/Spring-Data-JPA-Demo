package com.general.nitin.springjpa.controller;

import com.general.nitin.springjpa.dto.StudentRequest;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.service.StudentService;
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
    public Student createStudent(@RequestBody StudentRequest request) {
        return studentService.createStudent(
                request.getStudent(),
                request.getDepartmentId(),
                request.getCourseIds()
        );
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }
}