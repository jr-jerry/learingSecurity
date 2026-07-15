package com.ducat.learingSecurity.Config;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ducat.learingSecurity.Service.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private final CustomUserDetailService customUserDetailService;

    public JwtFilter(JwtUtility jwtUtility,
            com.ducat.learingSecurity.Service.CustomUserDetailService customUserDetailService) {
        this.jwtUtility = jwtUtility;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/v1/user/create") || path.startsWith("/api/v1/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        // Bearer token______
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String tokenUsername = jwtUtility.getUserName(token);

            if (tokenUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // tokenUsername-->db eess name se user hai kya ?
                UserDetails userDetails = customUserDetailService.loadUserByUsername(tokenUsername);
                String dbUserName = userDetails.getUsername();
                // if user exist in db -->then validate jwt token with that userName
                if (jwtUtility.validToken(token, dbUserName)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), List.of());

                    usernamePasswordAuthenticationToken.setDetails(request.getRemoteAddr());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
