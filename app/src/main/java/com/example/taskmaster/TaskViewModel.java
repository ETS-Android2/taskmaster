package com.example.taskmaster;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private final LiveData<List<TaskOld>> allTasks;

    public TaskViewModel (Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();

    }

    public LiveData<List<TaskOld>> getAllTasks() {
        return allTasks;
    }

    public void insert(TaskOld task) {
        taskRepository.insert(task);
    }
}
