package com.login.project.auth.jwt;

import com.login.project.auth.login.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface JwtService {
    String extractUserName(String token);
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
