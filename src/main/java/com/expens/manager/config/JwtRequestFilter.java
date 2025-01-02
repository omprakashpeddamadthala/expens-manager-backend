package com.expens.manager.config;


import com.expens.manager.service.CustomUserDetailsService;
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
    private  JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader =  request.getHeader( "Authorization" );

        String jwtToken = null;
        String email = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith( "Bearer" )){
            jwtToken = requestTokenHeader.substring( 7 );
            try{
                email =   jwtTokenUtil.getUsernameFromToken(jwtToken );
            }catch (IllegalArgumentException exception){
                throw new RuntimeException("Unable to get jwt token");
            }catch (ExpiredJwtException exception){
                throw new RuntimeException("Jwt token has expired");
            }

            if(email!=null && SecurityContextHolder.getContext().getAuthentication() ==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername( email );

                if(jwtTokenUtil.validateToken( jwtToken,userDetails )){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
                    SecurityContextHolder.getContext().setAuthentication( authenticationToken );
                }
            }
            filterChain.doFilter( request,response );
        }
    }
}
