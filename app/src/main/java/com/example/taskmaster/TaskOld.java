package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "tasks")
public class TaskOld {

    @PrimaryKey(autoGenerate = true)
    private int uid;


    private String title;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private String desc;
    private String state;

    public TaskOld() {
    }

    public TaskOld(String title, String desc, String state) {
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
