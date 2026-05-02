package com.general.nitin.springjpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createStudent_whenPayloadIsInvalid_returnsFieldWiseValidationErrors() throws Exception {
        String invalidRequestBody = """
                {
                  "student": {
                    "name": ""
                  },
                  "departmentId": 0,
                  "courseIds": [null, -5]
                }
                """;

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.response['student.name']").value("student.name is required"))
                .andExpect(jsonPath("$.response.departmentId").value("departmentId must be greater than 0"))
                .andExpect(jsonPath("$.response['courseIds[0]']").value("courseId must not be null"));
    }
}

