package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllTasks extends AppCompatActivity {
    static TaskDataBase db;
    List<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        db = TaskDataBase.getInstance(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
//        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.allTaskView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new TaskAdapter(tasks));
            }
        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        new Thread(() -> {
//            while (true) {
//                TaskDao taskDao = db.taskDao();
//                tasks = taskDao.gitAll();
//            }
//        }).start();
//
//        RecyclerView recyclerView = findViewById(R.id.allTaskView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TaskAdapter(tasks));
//    }

    public static List<Task> getList(SharedPreferences sharedPreferences) {
//        List<Task> tasks = new ArrayList<>();
//        String string = sharedPreferences.getString("taskList",null);
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Task>>(){}.getType();
//        tasks = gson.fromJson(string, type);
//        return tasks;
        return db.taskDao().gitAll();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}