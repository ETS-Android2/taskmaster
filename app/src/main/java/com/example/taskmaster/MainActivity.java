package com.example.taskmaster;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        assert result.getData() != null;
                        Task task = new Task(result.getData().getStringExtra("title"), result.getData().getStringExtra("desc"), "new");
                        taskViewModel.insert(task);
                    }
                }
            }
    );
    LiveData<List<Task>> tasks;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        tasks = taskViewModel.getAllTasks();
        List<Task> taskList = tasks.getValue();
        RecyclerView recyclerView = findViewById(R.id.mainTaskView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(taskList));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Anonymous");
        System.out.println(name);
        TextView textView = (TextView) findViewById(R.id.mainUsername);
        textView.setText(name + "'s Tasks");
    }


    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);


    }

    public static List<Task> seedTask(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("taskList", null);
        List<Task> tasks = new ArrayList<>();
        if (string == null) {
            tasks.add(new Task("Title1", "Desc1", "new "));
            tasks.add(new Task("Title2", "Desc2", "new "));
            tasks.add(new Task("Title3", "Desc3", "new "));
            tasks.add(new Task("Title4", "Desc4", "new "));
            tasks.add(new Task("Title5", "Desc5", "new "));
            tasks.add(new Task("Title6", "Desc6", "new "));
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(tasks);
            System.out.println(json);
            editor.putString("taskList", json);
            editor.apply();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Task>>() {
            }.getType();
            tasks = gson.fromJson(string, type);
        }
        return tasks;
    }

    public void showAddTask(View v) {

        Intent intent = new Intent(this, AddTask.class);
        activityResultLauncher.launch(intent);

    }

    public void showSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }
}