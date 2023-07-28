package com.login.project.auth.jwt;

import com.login.project.auth.jwt.dto.ReIssueTokenRequest;
import com.login.project.auth.jwt.dto.ReIssueTokenResponse;
import com.login.project.auth.login.AccountRepository;
import com.login.project.auth.login.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    public ReIssueTokenResponse reissue(ReIssueTokenRequest reIssueTokenRequest) throws Exception {
        String refreshToken = reIssueTokenRequest.getRefreshToken();
        String preAccessToken = reIssueTokenRequest.getAccessToken();

        String preAccountId = jwtService.extractUserName(preAccessToken);
        Account account = accountRepository.findById(preAccountId);

        //refreshToken id와 로그인한 사용자 계정 id 비교 및 만료 시간 검사
        if (!jwtService.isTokenValid(refreshToken, account)) {
            throw new Exception("refresh token expired");
        }

        //refreshToken id와 accessToken id 값 비교
        if (!preAccountId.equals(jwtService.extractUserName(refreshToken))) {
            throw new Exception("Not match accountId");
        }

        String newAccountToken = jwtService.generateAccessToken(account);
        ReIssueTokenResponse reIssueTokenResponse = ReIssueTokenResponse.builder()
                .accessToken(newAccountToken)
                .build();

//        System.out.println("AccountToken = " + newAccountToken);
        return reIssueTokenResponse;
    }
}
