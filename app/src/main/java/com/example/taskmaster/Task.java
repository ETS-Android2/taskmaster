package com.example.taskmaster;

import androidx.annotation.NonNull;

public class Task {
    private String title;
    private String desc;
    private String state;

    public Task() {
    }

    public Task(String title, String desc, String state) {
        this.title = title;
        this.desc = desc;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
