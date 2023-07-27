package com.login.project.filter;

import com.login.project.auth.login.entity.Account;
import com.login.project.util.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${JWT.SECRET}")
    private String SECRET_KEY;
    private final UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String tokenHeader = request.getHeader("Authorization");
            String jwtToken = null;

            if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer")) {
                jwtToken = tokenHeader.replace("Bearer ", "");
            }

            if (jwtToken != null && isValid(jwtToken)) {
                SecurityContextHolder.getContext().setAuthentication(getAuth(jwtToken));
            }
        } catch (Exception e) {
            // Handle JWT parsing or authentication exceptions gracefully
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuth(String jwtToken) {
        Account account = (Account) userServiceImpl.userDetailsService().loadUserByUsername(getEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword(), account.getAuthorities());
    }

    private String getEmail(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwtToken).getBody()
                .getSubject();
    }

    private boolean isValid(String jwtToken) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);

            return jws != null &&
                    jws.getBody().getSubject() != null &&
                    !jws.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    private Key getSecretKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}