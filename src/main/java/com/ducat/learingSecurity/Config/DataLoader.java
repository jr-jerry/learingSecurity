package com.ducat.learingSecurity.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ducat.learingSecurity.Entity.UserEntity;
import com.ducat.learingSecurity.Repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        UserEntity userEntity=UserEntity.builder()
                                        .username("gautam")
                                        //root123-->raw-->hash convert krke store --> 
                                        .password(passwordEncoder.encode("root123"))
                                        .Role("USER")
                                        .build();
        userRepository.save(userEntity);
    }
    
}
