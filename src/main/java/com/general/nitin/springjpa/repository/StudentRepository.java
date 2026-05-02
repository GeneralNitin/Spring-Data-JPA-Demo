package com.general.nitin.springjpa.repository;

import com.general.nitin.springjpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

//    @Query("")
//    List<Student> findAllByNameAndDepartment_Name(String name, String departmentName);
//

    @Query("SELECT s from Student s where s.name=:name and s.department.name=:departmentName")
    Optional<Student> findByNameAndDepartment_Name(@Param("name") String name, @Param("departmentName") String departmentName);
}