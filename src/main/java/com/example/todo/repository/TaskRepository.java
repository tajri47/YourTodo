package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.model.Task;
import com.example.todo.model.User;

public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByUser(User user);
    List<Task> findByUserAndCompleted(User user, boolean completed);


}