package com.login.project.exception.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    // Getters and Setters (you can use Lombok or generate them manually)

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }
}



