package com.ducat.learingSecurity.Config;

import java.net.http.HttpRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final UserDetailsService customUserDetailService;

    public SecurityConfiguration(UserDetailsService userDetailsService){
        this.customUserDetailService=userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
        .authenticationProvider(getAuthenticationProvider())
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(
            auth->auth
                    .requestMatchers(HttpMethod.POST,"/api/v1/user/create").permitAll()
                      .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .build();
    } 
    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }     
}
