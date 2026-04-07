package com.general.nitin.springjpa.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private int status;
    private T response;

    public ApiResponse(int status, T response) {
        this.status = status;
        this.response = response;
    }

    public static <T> ApiResponse<T> of(int statusCode, T response) {
        return new ApiResponse<>(statusCode, response);
    }
}


