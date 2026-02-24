package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;



    public List<Task> getPendingTasks(User user){

        return repo.findByUserAndCompleted(user, false);

    }



    public List<Task> getCompletedTasks(User user){

        return repo.findByUserAndCompleted(user, true);

    }



    public void save(Task task){

        repo.save(task);

    }



    public Task getById(Long id){

        return repo.findById(id).orElse(null);

    }



    public void deleteTask(Long id){

        repo.deleteById(id);

    }

}