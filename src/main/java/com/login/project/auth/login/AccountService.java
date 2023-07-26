package com.login.project.auth.login;

import com.login.project.auth.jwt.dto.TokenResponse;
import com.login.project.auth.login.dto.LoginDto;
import com.login.project.auth.login.dto.LogoutDto;
import com.login.project.auth.login.dto.SignupRequest;

public interface AccountService {
    TokenResponse login(LoginDto.LoginRequest request);
    TokenResponse signup(SignupRequest request);
    LogoutDto.LogoutResponse logout(LogoutDto.LogoutRequest logoutRequest) throws Exception;
}
