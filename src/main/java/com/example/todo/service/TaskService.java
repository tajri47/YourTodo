package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.repository.TaskRepository;

import org.springframework.data.domain.Sort;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public List<Task> getPendingTasks(User user, String sortType) {

        Sort sort = getSort(sortType);

        return repo.findByUserAndCompleted(user, false, sort);
    }

    public List<Task> getCompletedTasks(User user, String sortType) {

        Sort sort = getSort(sortType);

        return repo.findByUserAndCompleted(user, true, sort);
    }

    public void save(Task task) {

        repo.save(task);

    }

    public Task getById(Long id) {

        return repo.findById(id).orElse(null);

    }

    public void deleteTask(Long id) {

        repo.deleteById(id);

    }

    private Sort getSort(String sortType) {

        if ("desc".equals(sortType)) {
            return Sort.by("dueDate").descending();
        }

        return Sort.by("dueDate").ascending();
    }

    // Delete All buttons
    public void deleteAllPendingTasks(User user) {
        List<Task> pendingTasks = repo.findByUserAndCompleted(user, false);
        repo.deleteAll(pendingTasks);
    }

    public void deleteAllCompletedTasks(User user) {
        List<Task> completedTasks = repo.findByUserAndCompleted(user, true);
        repo.deleteAll(completedTasks);
    }

}