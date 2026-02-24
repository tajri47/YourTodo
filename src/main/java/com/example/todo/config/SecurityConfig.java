package com.example.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.example.todo.service.GoogleOAuth2SuccessHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private GoogleOAuth2SuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())

        .headers(headers -> headers.frameOptions(frame -> frame.disable()))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/register","/check-username/**", "/h2-console/**","/animations/**","/css/**","/js/**","/images/**").permitAll()
            .anyRequest().authenticated()
        )

        .formLogin(login -> login
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/dashboard", true)
            .permitAll()
        )

        .oauth2Login(oauth -> oauth
            .loginPage("/login")
            .successHandler(successHandler)
        )

        .logout(logout -> logout
            .logoutSuccessUrl("/login")
            .permitAll()
        );

        return http.build();

    }

}