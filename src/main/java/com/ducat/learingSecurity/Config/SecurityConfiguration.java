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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final UserDetailsService customUserDetailService;
    private final JwtFilter jwtFilter;

    
    
    public SecurityConfiguration(UserDetailsService customUserDetailService, JwtFilter jwtFilter) {
        this.customUserDetailService = customUserDetailService;
        this.jwtFilter = jwtFilter;
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
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
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
