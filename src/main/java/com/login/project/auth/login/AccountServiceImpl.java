package com.login.project.auth.login;

import com.login.project.auth.jwt.RefreshTokenRepository;
import com.login.project.auth.jwt.dto.TokenResponse;
import com.login.project.auth.login.dto.LoginDto;
import com.login.project.auth.login.dto.LogoutDto;
import com.login.project.auth.login.dto.SignupRequest;
import com.login.project.auth.login.entity.Account;
import com.login.project.auth.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenResponse login(LoginDto.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));

        var account = accountRepository.findAccountById(request.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid id or password"));

        String refreshToken = jwtService.generateRefreshToken(account);
        String accessToken = jwtService.generateAccessToken(account);

        return TokenResponse.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse signup(SignupRequest request) {
        var account = Account.builder().id(request.getId()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        accountRepository.save(account);

        String refreshToken = jwtService.generateRefreshToken(account);
        String accessToken = jwtService.generateRefreshToken(account);

        return TokenResponse.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();
    }

    @Transactional
    @Override
    public LogoutDto.LogoutResponse logout(LogoutDto.LogoutRequest logoutRequest) throws Exception {
        String accountId = jwtService.extractUserName(logoutRequest.getAccessToken());
        var account = accountRepository.findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        var refreshToken = refreshTokenRepository.findByAccount(account);
        if(refreshToken==null) {
            throw new Exception();
        }
        refreshTokenRepository.deleteAllByAccount(account);

        return LogoutDto.LogoutResponse.builder()
                .status("OK")
                .build();
    }
}
