package com.login.project.account;

import com.login.project.auth.jwt.JwtService;
import com.login.project.auth.login.AccountRepository;
import com.login.project.auth.login.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Test
    void createAccountTest() {
        Account account = Account.builder()
                .uid(1)
                .id("testId")
                .password(encoder.encode("password123")) // 비밀번호 해시화
                .build();
        accountRepository.save(account);
    }

    @Test
    void extractUserNameTest() {
        Account account = Account.builder()
                .uid(1)
                .id("UserId")
                .password(encoder.encode("UserPass")) // 비밀번호 해시화
                .build();

        String accessToken = jwtService.generateAccessToken(account);
        String accountId = jwtService.extractUserName(accessToken);
        System.out.println(accountId);
    }
}
