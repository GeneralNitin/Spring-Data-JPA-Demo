package com.general.nitin.springjpa.service;

import com.general.nitin.springjpa.entity.Course;
import com.general.nitin.springjpa.entity.Department;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.exception.ResourceNotFoundException;
import com.general.nitin.springjpa.repository.CourseRepository;
import com.general.nitin.springjpa.repository.DepartmentRepository;
import com.general.nitin.springjpa.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
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
        studentRepository.save(student);

        // Set Courses (ManyToMany)
        List<Course> courses = courseRepository.findAllById(courseIds);
        validateCourseIds(courseIds, courses);
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

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void validateCourseIds(List<Long> requestedCourseIds, List<Course> courses) {
        Set<Long> foundCourseIds = courses.stream()
                .map(Course::getId)
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));

        List<Long> missingCourseIds = requestedCourseIds.stream()
                .filter(courseId -> !foundCourseIds.contains(courseId))
                .distinct()
                .toList();

        if (!missingCourseIds.isEmpty()) {
            throw new ResourceNotFoundException("Course not found with id(s): " + missingCourseIds);
        }
    }
}