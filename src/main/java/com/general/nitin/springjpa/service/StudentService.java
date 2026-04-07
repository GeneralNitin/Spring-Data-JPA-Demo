package com.general.nitin.springjpa.service;

import com.general.nitin.springjpa.entity.Course;
import com.general.nitin.springjpa.entity.Department;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.exception.ResourceNotFoundException;
import com.general.nitin.springjpa.repository.CourseRepository;
import com.general.nitin.springjpa.repository.DepartmentRepository;
import com.general.nitin.springjpa.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,
                          DepartmentRepository departmentRepository,
                          CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    public Student createStudent(Student student, Long departmentId, List<Long> courseIds) {

        // Set Department (ManyToOne)
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        student.setDepartment(department);

        // Set Courses (ManyToMany)
        List<Course> courses = courseRepository.findAllById(courseIds);
        student.setCourses(courses);

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}