package com.login.project.auth.jwt;

import com.login.project.auth.jwt.dto.ReIssueTokenRequest;
import com.login.project.auth.jwt.dto.ReIssueTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ReIssueTokenResponse> reissue(@RequestBody ReIssueTokenRequest reIssueTokenRequest) throws Exception {
        return ResponseEntity.ok(tokenService.reissue(reIssueTokenRequest));
    }
}