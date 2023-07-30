package com.login.project.auth.jwt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReissueTokenDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReIssueTokenRequest {
        private String refreshToken;
        private String accessToken;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReIssueTokenResponse {
        private String accessToken;
    }
}
