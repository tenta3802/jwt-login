package com.login.project.auth.login;

import com.login.project.auth.jwt.JwtService;
import com.login.project.auth.jwt.RefreshTokenRepository;
import com.login.project.auth.jwt.entity.RefreshToken;
import com.login.project.auth.login.dto.LoginDto;
import com.login.project.auth.login.dto.LogoutDto;
import com.login.project.auth.login.dto.SignupRequest;
import com.login.project.auth.login.entity.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginDto.LoginResponse login(LoginDto.LoginRequest request) {
        //인증(id, password 검증)
        String id = request.getUserId();
        String password = request.getUserPassword();

        Account account = accountRepository.findAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id OR Password"));

        if (passwordEncoder.matches(passwordEncoder.encode(password), account.getPassword())) {
            throw new IllegalArgumentException("Not Match Password");
        }

        //인가 -> 토큰 발급
        String refreshToken = jwtService.generateRefreshToken(account);
        String accessToken = jwtService.generateAccessToken(account);

        RefreshToken tokenEntity = refreshTokenRepository.findRefreshTokenByAccount(account);

        // refreshToken 존재 여부 확인에 따른 객체 저장
        if (tokenEntity != null) {
            tokenEntity.setRefreshToken(refreshToken);
            tokenEntity.setExpireAt(LocalDateTime.now().plusMinutes(2));
        } else {
            tokenEntity = RefreshToken.builder()
                    .uid(0)
                    .account(account)
                    .refreshToken(refreshToken)
                    .expireAt(LocalDateTime.now().plusMinutes(2))
                    .build();
        }
        refreshTokenRepository.save(tokenEntity);

        return LoginDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void signup(SignupRequest request) {
        Account account = Account.builder()
                .id(request.getUserId())
                .password(passwordEncoder.encode(request.getUserPassword()))
                .build();
        accountRepository.save(account);
    }

    @Transactional
    public LogoutDto.LogoutResponse logout(LogoutDto.LogoutRequest logoutRequest) {
        String accountId = jwtService.extractUserName(logoutRequest.getAccessToken());
        Account account = accountRepository.findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        List<RefreshToken> refreshToken = refreshTokenRepository.findByAccount(account);
        if (refreshToken == null) {
            throw new NullPointerException("RefreshToken is Null");
        }
        refreshTokenRepository.deleteAllByAccount(account);

        return LogoutDto.LogoutResponse.builder()
                .status("OK")
                .build();
    }
}
