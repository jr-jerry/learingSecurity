package com.ducat.learingSecurity.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ducat.learingSecurity.Entity.UserEntity;
import com.ducat.learingSecurity.Repository.UserRepository;

@Service
public class UserEntityService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    
     
    public UserEntityService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public UserEntity saveUserEntity(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
