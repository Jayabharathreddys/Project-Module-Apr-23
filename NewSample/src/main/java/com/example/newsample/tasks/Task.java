package com.example.newsample.tasks;

import java.util.Date;

public class Task {
    Integer id;
    String name;
    Date dueDate;
    Boolean completed;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Task(Integer id, String name, Date dueDate, Boolean completed) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.completed = completed;
    }


}
