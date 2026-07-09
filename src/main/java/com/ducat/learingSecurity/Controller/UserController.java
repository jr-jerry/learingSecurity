package com.ducat.learingSecurity.Controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducat.learingSecurity.Config.JwtUtility;
import com.ducat.learingSecurity.Entity.UserEntity;
import com.ducat.learingSecurity.Service.UserEntityService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
/**
 * api/v1/user/create
 * UserController
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    // @Value("${DB_URL}")
    // private String jdbcUrl;
    private final UserEntityService userEntityService;
    private final JwtUtility jwtUtility;
    
    

    public UserController(UserEntityService userEntityService, JwtUtility jwtUtility) {
        this.userEntityService = userEntityService;
        this.jwtUtility = jwtUtility;
    }



    @PostMapping("/create")
    public ResponseEntity<?> createUserEndpoint(@RequestBody UserEntity userEntity ){
        //user ko save krna hoga db p 
        userEntityService.saveUserEntity(userEntity);
        
        // hme jwt token bana hoga ! and retur krna hoga ! 
        String jwtToken=jwtUtility.generateToken(userEntity.getUsername());
        return new ResponseEntity<>(Map.of("token",jwtToken),HttpStatus.CREATED);
    }
}
