package com.ducat.learingSecurity.Controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducat.learingSecurity.Config.JwtUtility;
import com.ducat.learingSecurity.DTO.UserRequest;
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
    private final AuthenticationManager authenticationManager;
    // @Value("${DB_URL}")
    // private String jdbcUrl;
    private final UserEntityService userEntityService;
    private final JwtUtility jwtUtility;
    
    

    public UserController(UserEntityService userEntityService, JwtUtility jwtUtility,AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userEntityService = userEntityService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUserEndpoint(@RequestBody UserRequest userRequest ){
       UserDetails userDetails= User.builder()
        .username(userRequest.getUsername())
        .password(userRequest.getPassword())
        .roles("USER")
        .build();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userRequest.getPassword(),List.of()));

        String token=jwtUtility.generateToken(userRequest.getUsername());
        return new ResponseEntity<>(Map.of("access_token",token),HttpStatus.ACCEPTED);
    }



    @PostMapping("/create")
    public ResponseEntity<?> createUserEndpoint(@RequestBody UserEntity userEntity ){
        //user ko save krna hoga db p 
        UserEntity userEntitySaved=userEntityService.saveUserEntity(userEntity);

        return new ResponseEntity<>(Map.of("Data",userEntitySaved),HttpStatus.CREATED);
        
        
    }
}
