package com.ducat.learingSecurity.Config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtility {
    private final static int expiredTime=1000*60*60;
    private final static Key secretKey=Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String key="O2FzbGtmajtsYWtzamY7bGFzZmo7c2FsZGtmO3NhZGw=";

    public String generateToken(String username)
    {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+expiredTime))
            .signWith(getKey())
            .compact();
    }
    public Key getKey(){
        byte[] keyBytes=Decoders.BASE64URL.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
