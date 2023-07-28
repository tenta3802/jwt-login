package com.login.project.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
