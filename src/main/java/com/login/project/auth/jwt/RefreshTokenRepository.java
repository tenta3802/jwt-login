package com.login.project.auth.jwt;

import com.login.project.auth.jwt.entity.RefreshToken;
import com.login.project.auth.login.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    List<RefreshToken> findByAccount(Account account);
    void deleteAllByAccount(Account account);
}
