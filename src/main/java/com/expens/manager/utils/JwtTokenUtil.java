package com.expens.manager.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenUtil {

    public static final long  JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value( "${jwt.secret}" )
    private String secret;

    public String generateToken (UserDetails userDetails){
        Map <String,Object > claims=new HashMap<>();

        return Jwts.builder()
                .setClaims( claims )
                .setSubject( userDetails.getUsername() )
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration( new Date(System.currentTimeMillis() +JWT_TOKEN_VALIDITY ))
                .signWith( SignatureAlgorithm.HS256,secret )
                .compact();
    }
}
