package com.example.taskmaster;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskOld>> allTasks;

    TaskRepository (Application application) {
        TaskDataBase db = TaskDataBase.getInstance(application);
        taskDao = db.taskDao();
        allTasks = taskDao.gitAll();

    }


    LiveData<List<TaskOld>> getAllTasks() {
        return allTasks;
    }

    void insert(TaskOld task) {
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }
}
