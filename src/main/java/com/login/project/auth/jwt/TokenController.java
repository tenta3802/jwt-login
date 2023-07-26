package com.login.project.auth.jwt;

import com.login.project.auth.jwt.dto.ReIssueTokenRequest;
import com.login.project.auth.jwt.dto.ReIssueTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ReIssueTokenResponse reissue(@RequestBody ReIssueTokenRequest reIssueTokenRequest) throws Exception {
        return tokenService.reissue(reIssueTokenRequest);
    }
}
