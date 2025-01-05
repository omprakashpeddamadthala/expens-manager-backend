package com.expens.manager.config;

import com.expens.manager.service.CustomUserDetailsService;
import com.expens.manager.service.impl.TokenBlackListService;
import com.expens.manager.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractTokenFromHeader(request);
        if ( isValidToken(jwtToken)) {
            handleInvalidToken(response);
            return;
        }

        String email =   jwtTokenUtil.getUsernameFromToken(jwtToken );
        UserDetails userDetails = loadUserDetails(email);
        if (!validateToken(jwtToken, userDetails)) {
            throw new RuntimeException("Unable to validate JWT token");
        }

        authenticateUser(request, userDetails);

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login") || request.getServletPath().equals("/register");
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") ? requestTokenHeader.substring(7) : null;
    }

    private boolean isValidToken(String jwtToken) {
        return jwtToken != null && tokenBlackListService.isTokenIsBlockListed(jwtToken);
    }

    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
    }

    private UserDetails loadUserDetails(String email) {
        return userDetailsService.loadUserByUsername(email);
    }

    private boolean validateToken(String jwtToken, UserDetails userDetails) {
        try {
            return jwtTokenUtil.validateToken(jwtToken, userDetails);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unable to get JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired", e);
        }
    }

    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
