package com.login.project.auth.login.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class LoginRequest {
        private String id;
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
    }
}