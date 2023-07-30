package com.login.project.auth.jwt;

import com.login.project.auth.jwt.dto.ReissueTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenDto.ReIssueTokenResponse> reissue(
            @RequestBody ReissueTokenDto.ReIssueTokenRequest reIssueTokenRequest) throws Exception {
        return ResponseEntity.ok(tokenService.reissue(reIssueTokenRequest));
    }
}