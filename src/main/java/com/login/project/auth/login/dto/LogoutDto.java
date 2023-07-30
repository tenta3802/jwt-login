package com.login.project.auth.login.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LogoutDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutRequest {
        private String accessToken;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutResponse {
        private String status;
    }
}
