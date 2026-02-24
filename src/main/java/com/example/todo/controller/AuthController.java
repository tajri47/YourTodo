package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.todo.model.User;
import com.example.todo.service.AuthService;
import com.example.todo.service.UserService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;



    @GetMapping("/")
    public String home(){

        return "redirect:/login";

    }



    @GetMapping("/login")
    public String login(){

        return "login";

    }



    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("user", new User());

        return "register";

    }



    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(
            @RequestParam String username){

        User user =
            userService.findByUsername(username);

        if(user != null){

            return "TAKEN";

        }

        return "AVAILABLE";

    }



    @PostMapping("/register")
    public String registerUser(
            @Valid User user,
            BindingResult result,
            Model model){

        if(result.hasErrors()){

            return "register";

        }

        String response =
            authService.register(user);

        switch(response){

            case "USERNAME_EXISTS":

                model.addAttribute(
                    "error",
                    "Username already exists");

                return "register";


            case "EMAIL_EXISTS":

                model.addAttribute(
                    "error",
                    "Email already exists");

                return "register";


            case "PASSWORD_MISMATCH":

                model.addAttribute(
                    "error",
                    "Passwords do not match");

                return "register";


            case "WEAK_PASSWORD":

                model.addAttribute(
                    "error",
                    "Weak password");

                return "register";


            default:

                return "redirect:/login";

        }

    }

}