package com.portfolio.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret.id_access}")
    private String ACCESS_SECRET_KEY;
    
    @Value("${jwt.secret.id_refresh}")
    private String REFRESH_SECRET_KEY;

    
    public String generateAccessToken(String userId) throws Exception{
        Map<String, Object> claims = new HashMap<>();
        return createAccessToken(claims, userId);
    }
    
    public String generateRefreshToken(String userId) throws Exception{
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userId);
    }

    private String createAccessToken(Map<String, Object> claims, String subject) throws Exception{
    	
        return Jwts.builder()
        		.claims(claims)
        		.subject(subject)
        		.issuedAt(new Date(System.currentTimeMillis()))
        		.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))		//10시간
        		.signWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .compact();
    }
    
    private String createRefreshToken(Map<String, Object> claims, String subject) throws Exception{
    	
        return Jwts.builder()
        		.claims(claims)
        		.subject(subject)
        		.issuedAt(new Date(System.currentTimeMillis()))
        		.expiration(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))	//30일
        		.signWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .compact();
    }
    
    

//    public Boolean validateToken(String token, String userId) throws Exception{
//        final String extractedUserId = extractUserId(token);
//        return (extractedUserId.equals(userId) && !isTokenExpired(token));
//    }

//    public String extractUserId(String token) throws Exception{
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) throws Exception{
//        return extractClaim(token, Claims::getExpiration);
//    }


    public Claims extractAllClaimsByAccessToken(String token) throws Exception{
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .build().parseSignedClaims(token).getPayload();
    }
    
    public Claims extractAllClaimsByRefreshToken(String token) throws Exception{
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .build().parseSignedClaims(token).getPayload();
    }

//    private Boolean isTokenExpired(String token) throws Exception{
//        return extractExpiration(token).before(new Date());
//    }
}
