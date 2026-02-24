package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    private boolean isStrongPassword(String password){

        if(password.length() < 8) return false;

        boolean hasLetter = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for(char c : password.toCharArray()){

            if(Character.isLetter(c)) hasLetter = true;
            else if(Character.isDigit(c)) hasNumber = true;
            else hasSpecial = true;

        }

        return hasLetter && hasNumber && hasSpecial;
    }

    public boolean usernameExists(String username){

        return repo.existsByUsername(username);

    }

    public String register(User user){

        if(repo.existsByUsername(user.getUsername())){
            return "USERNAME_EXISTS";
        }

        if(repo.existsByEmail(user.getEmail())){
            return "EMAIL_EXISTS";
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            return "PASSWORD_MISMATCH";
        }

        if(!isStrongPassword(user.getPassword())){
            return "WEAK_PASSWORD";
        }

        user.setPassword(
            encoder.encode(user.getPassword())
        );

        repo.save(user);

        return "SUCCESS";
    }

}