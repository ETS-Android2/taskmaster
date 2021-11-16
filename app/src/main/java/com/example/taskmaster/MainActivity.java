package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = sharedPreferences.getString("name", "Anonymous");
        System.out.println(name);
        TextView textView = (TextView) findViewById(R.id.mainUsername);
        textView.setText(name + "'s Tasks");
        List<Task> tasks = new ArrayList<>();
        tasks = seedTask(sharedPreferences);
        RecyclerView recyclerView = findViewById(R.id.mainTaskView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasks.subList(0,3)));
    }

    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);

    }

    public static List<Task> seedTask (SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("taskList",null);
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
            Type type = new TypeToken<List<Task>>(){}.getType();
            tasks = gson.fromJson(string, type);
        }
        return tasks;
    }

    public void showAddTask(View v) {

        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void showSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }


}