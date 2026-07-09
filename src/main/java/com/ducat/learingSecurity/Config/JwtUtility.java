package com.ducat.learingSecurity.Config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
    public boolean validToken(String token,String username){
        /**
         * 1-Token expire nahi hona chea-->claims--getExpired -->expiredTime > currentTime -->token expired ! 
         * 2-token k username and receive username same hona chea 
         */
        boolean isExpired=isExpired(token);
        String tokenUsername=getUserName(token);

       return (!isExpired && tokenUsername.equals(username));

    }
    public String getUserName(String token){
        //extract payload 
        Claims claims=getClaims(token);
        String username=claims.getSubject();

        return username;

    }
    public boolean isExpired(String token){
        Claims claims=getClaims(token);
        Date tokenDate=claims.getExpiration();

        Date currentDate=new Date(System.currentTimeMillis());
        return currentDate.after(tokenDate);
    }
    //expire -->token date >>> current date -->true 
    // token date << current date -->false
    public Claims getClaims(String token){
       return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
    public Key getKey(){
        byte[] keyBytes=Decoders.BASE64URL.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
