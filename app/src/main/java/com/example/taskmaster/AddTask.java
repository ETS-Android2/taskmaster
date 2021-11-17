package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AddTask extends AppCompatActivity {
    static TaskDataBase db;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = TaskDataBase.getInstance(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
//        List<Task> tasks = getList(sharedPreferences);
//        TextView textView = findViewById(R.id.totalTask);
//        textView.setText("Total Task: "+tasks.size());


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


    @SuppressLint("SetTextI18n")
    public void addTask(View view) {
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
//        List<Task> tasks = getList(sharedPreferences);
        TextInputEditText title = findViewById(R.id.title);
        TextInputEditText desc = findViewById(R.id.desc);
        Task task = new Task(Objects.requireNonNull(title.getText()).toString(), Objects.requireNonNull(desc.getText()).toString(), "new");
//        tasks.add(0,task);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                addList(task);
            }
        });

        TextView textView = findViewById(R.id.totalTask);
        String[] arrayOfText = textView.getText().toString().split(":");
        String firstHalf = arrayOfText[0];
        String textNumber = arrayOfText[1].split(" ")[1];
//        System.out.println(Integer.parseInt(textNumber)+5);
        int number = Integer.parseInt(textNumber) + 1;
        textView.setText(firstHalf + ": " + number);
        Toast.makeText(getApplicationContext(), "submitted!!", Toast.LENGTH_LONG).show();
    }

    public static List<Task> getList(SharedPreferences sharedPreferences) {
//        List<Task> tasks = new ArrayList<>();
//        String string = sharedPreferences.getString("taskList",null);
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Task>>(){}.getType();
//        tasks = gson.fromJson(string, type);
//        return tasks;
        return db.taskDao().gitAll();
    }

    public static void addList (
//            @SuppressLint("CommitPrefEdits")SharedPreferences.Editor editor,
            Task task) {
//        Gson gson = new Gson();
//        String json = gson.toJson(tasks);
//        System.out.println(json);
//        editor.putString("taskList", json);
//        editor.apply();
        db.taskDao().insertTask(task);
    }
}