package com.general.nitin.springjpa.dto.request;

import com.general.nitin.springjpa.entity.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "student details are required")
    @Valid
    private Student student;

    @NotNull(message = "departmentId is required")
    @Positive(message = "departmentId must be greater than 0")
    private Long departmentId;

    @NotEmpty(message = "courseIds must contain at least one course")
    private List<@NotNull(message = "courseId must not be null")
            @Positive(message = "courseId must be greater than 0") Long> courseIds;
}