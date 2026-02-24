package com.example.todo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private LocalDate dueDate;

    private boolean completed;

    private LocalDate completedDate;

    @ManyToOne
    private User user;



    // Default constructor
    public Task(){}



    // Getters and setters

    public Long getId() {
        return id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;

        if(completed){

            this.completedDate = LocalDate.now();

        } else {

            this.completedDate = null;

        }
    }



    public LocalDate getCompletedDate() {
        return completedDate;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    // PRIORITY CALCULATION
    // This is NOT stored in database
    // It is calculated dynamically

    @Transient
    public String getPriority(){

        if(dueDate == null){

            return "NO DATE";

        }

        long days =
                ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        dueDate
                );

        if(days <= 1){

            return "HIGH";

        }
        else if(days <= 3){

            return "MEDIUM";

        }
        else{

            return "LOW";

        }

    }

}