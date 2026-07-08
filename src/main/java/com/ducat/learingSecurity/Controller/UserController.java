package com.ducat.learingSecurity.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducat.learingSecurity.Entity.UserEntity;
/**
 * api/v1/user/create
 * UserController
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @PostMapping("/create")
    public ResponseEntity<?> createUserEndpoint(@RequestBody UserEntity userEntity ){
        return new ResponseEntity<>("Without authentication",HttpStatus.ACCEPTED);
    }
}
