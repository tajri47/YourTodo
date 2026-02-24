package com.example.todo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository repo;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email =
                oauthUser.getAttribute("email");

        String name =
                oauthUser.getAttribute("name");


        // CHECK BY EMAIL
        User user = repo.findByEmail(email);


        // CHECK BY USERNAME ALSO
        if(user == null){

            user = repo.findByUsername(email);

        }


        // IF USER DOES NOT EXIST → CREATE
        if(user == null){

            user = new User();

            user.setEmail(email);

            user.setUsername(email);

            user.setFullName(name);

            user.setPassword("GOOGLE_USER");

            repo.save(user);

        }


        // IF USER EXISTS → JUST LOGIN

        response.sendRedirect("/dashboard");

    }

}