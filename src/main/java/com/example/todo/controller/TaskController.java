// TaskController
//    |
//    |--- GET /tasks
//    |--- POST /tasks
//    |--- GET /tasks/1
//    |--- PUT /tasks/1
//    |--- DELETE /tasks/1

package com.example.todo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.todo.model.Task;
import com.example.todo.model.User;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserService;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            Principal principal,
            @RequestParam(required = false) String pendingSort,
            @RequestParam(required = false) String completedSort) {

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("pendingTasks",
                taskService.getPendingTasks(user, pendingSort));

        model.addAttribute("completedTasks",
                taskService.getCompletedTasks(user, completedSort));

        model.addAttribute("pendingSort", pendingSort);
        model.addAttribute("completedSort", completedSort);

        model.addAttribute("task", new Task());

        model.addAttribute("username", user.getUsername());

        model.addAttribute("fullName", user.getFullName());

        return "dashboard";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute Task task,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());
        task.setUser(user);

        task.setCompleted(false);

        taskService.save(task);

        return "redirect:/dashboard";
    }

    @GetMapping("/toggle/{id}")
    public void toggleTask(@PathVariable Long id) {

        Task task = taskService.getById(id);

        task.setCompleted(!task.isCompleted());

        taskService.save(task);
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "redirect:/dashboard";
    }

    // delete all

    @GetMapping("/tasks/deleteAllPending")
    public String deleteAllPending(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        taskService.deleteAllPendingTasks(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/tasks/deleteAllCompleted")
    public String deleteAllCompleted(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        taskService.deleteAllCompletedTasks(user);
        return "redirect:/dashboard";
    }

}