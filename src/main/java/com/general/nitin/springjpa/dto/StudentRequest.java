package com.general.nitin.springjpa.dto;

import com.general.nitin.springjpa.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    private Student student;
    private Long departmentId;
    private List<Long> courseIds;
}