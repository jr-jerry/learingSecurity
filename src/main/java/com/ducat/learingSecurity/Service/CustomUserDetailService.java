package com.ducat.learingSecurity.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ducat.learingSecurity.Entity.UserEntity;
import com.ducat.learingSecurity.Repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    
    private final UserRepository userRepository;
    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> box=userRepository.findByUsername(username);
        if(box.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        UserEntity userEntity_found=box.get();
        
         UserDetails userDetails=User.builder()
                                        .username(userEntity_found.getUsername())
                                        .password(userEntity_found.getPassword())
                                        .roles("USER")
                                        .build();

        return userDetails;
    }
    
}