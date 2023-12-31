//package com.login.project.filter;
//
//import com.login.project.auth.jwt.JwtService;
//import com.login.project.util.UserService;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    private final UserService userService;
//    private static int count = 0;
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//
//        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
////        System.out.println("jwt = " + jwt.getClass().getName());
//        if (jwt.getClass().getName()=="undefined") {
//            userEmail = jwtService.extractUserName(jwt);
//            if (StringUtils.isEmpty(userEmail)
//                    && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userService.userDetailsService()
//                        .loadUserByUsername(userEmail);
//                if (jwtService.isTokenValid(jwt, userDetails)) {
//                    SecurityContext context = SecurityContextHolder.createEmptyContext();
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    context.setAuthentication(authToken);
//                    SecurityContextHolder.setContext(context);
//                }
//            }
//        }
//    }
//}
