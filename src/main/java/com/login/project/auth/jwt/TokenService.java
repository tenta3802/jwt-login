package com.login.project.auth.jwt;

import com.login.project.auth.jwt.dto.ReissueTokenDto;
import com.login.project.auth.jwt.entity.RefreshToken;
import com.login.project.auth.login.AccountRepository;
import com.login.project.auth.login.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public ReissueTokenDto.ReIssueTokenResponse reissue(ReissueTokenDto.ReIssueTokenRequest reIssueTokenRequest) throws Exception {
        String preRefreshToken = reIssueTokenRequest.getRefreshToken();
        String preAccessToken = reIssueTokenRequest.getAccessToken();

        String preAccountId = jwtService.extractUserName(preAccessToken);
        Account account = accountRepository.findById(preAccountId);

        if(account == null) {
            throw new Exception("Not found account");
        }

        //refreshToken과 DB에 저장된 refreshToken 일치 여부 확인
        if(!refreshTokenRepository.findRefreshTokenByAccount(account).getRefreshToken().equals(preRefreshToken)) {
            throw new Exception("Not match refreshToken");
        }

        //refreshToken id와 로그인 한 사용자 계정 id 비교 및 만료 시간 검사
        if (!jwtService.isTokenValid(preRefreshToken, account)) {
            throw new Exception("refresh token expired");
        }

        //refreshToken id와 accessToken id 값 비교
        if (!preAccountId.equals(jwtService.extractUserName(preRefreshToken))) {
            throw new Exception("Not match accountId");
        }

        String newAccountToken = jwtService.generateAccessToken(account);
        ReissueTokenDto.ReIssueTokenResponse reIssueTokenResponse = ReissueTokenDto.ReIssueTokenResponse.builder()
                .accessToken(newAccountToken)
                .build();

        return reIssueTokenResponse;
    }
}
