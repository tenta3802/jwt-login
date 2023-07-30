package com.login.project.auth.login;

import com.login.project.auth.login.dto.LoginDto;
import com.login.project.auth.login.dto.LogoutDto;
import com.login.project.auth.login.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto.LoginResponse> login(@RequestBody LoginDto.LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.login(loginRequest));
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
        accountService.signup(signupRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDto.LogoutResponse> logout(@RequestBody LogoutDto.LogoutRequest logoutRequest) {
        return ResponseEntity.ok(accountService.logout(logoutRequest));
    }
}