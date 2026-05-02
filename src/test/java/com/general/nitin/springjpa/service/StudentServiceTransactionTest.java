package com.general.nitin.springjpa.service;

import com.general.nitin.springjpa.entity.Course;
import com.general.nitin.springjpa.entity.Department;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.exception.ResourceNotFoundException;
import com.general.nitin.springjpa.repository.CourseRepository;
import com.general.nitin.springjpa.repository.DepartmentRepository;
import com.general.nitin.springjpa.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StudentServiceTransactionTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void cleanDatabase() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void createStudent_whenAnyCourseIsMissing_rollsBackStudentInsert() {
        Department department = departmentRepository.save(new Department(null, "Science", List.of()));
        Course validCourse = courseRepository.save(new Course(null, "Mathematics", List.of()));

        Student student = new Student();
        student.setName("Nitin");

        assertThatThrownBy(() -> studentService.createStudent(
                student,
                department.getId(),
                List.of(validCourse.getId(), 999_999L)
        ))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Course not found");

        assertThat(studentRepository.count()).isZero();
    }
}

