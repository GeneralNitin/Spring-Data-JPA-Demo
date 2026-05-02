package com.general.nitin.springjpa;

import com.general.nitin.springjpa.entity.Address;
import com.general.nitin.springjpa.entity.Course;
import com.general.nitin.springjpa.entity.Department;
import com.general.nitin.springjpa.entity.Student;
import com.general.nitin.springjpa.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class SpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(StudentRepository studentRepo,
                          DepartmentRepository deptRepo,
                          CourseRepository courseRepo) {
        return args -> {

            // 1. Save Department first
            Department cs = new Department();
            cs.setName("Computer Science");
            deptRepo.save(cs);

            // 2. Save Courses first
            Course java = new Course();
            java.setTitle("Java");

            Course spring = new Course();
            spring.setTitle("Spring Boot");

            courseRepo.saveAll(List.of(java, spring));

            // 3. Create Address (will be cascaded)
            Address addr = new Address();
            addr.setCity("Bangalore");
            addr.setStreet("Main Road");

            // 4. Create Student
            Student s = new Student();
            s.setName("Nitin");
            s.setAddress(addr);          // OneToOne (cascade works)
            s.setDepartment(cs);         // ManyToOne (already saved)
            s.setCourses(List.of(java, spring)); // ManyToMany (already saved)

            // 5. Save Student
            studentRepo.save(s);
        };
    }
}
