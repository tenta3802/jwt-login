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
    public class ReIssueTokenRequest {
        private String RefreshToken;
        private String AccessToken;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ReIssueTokenResponse {
        private String accessToken;
    }
}
