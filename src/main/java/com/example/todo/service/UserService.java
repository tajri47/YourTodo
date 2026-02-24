package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public void register(User user){
        repo.save(user);
    }
    public User findByUsername(String username){
        return repo.findByUsername(username);
    }
}
