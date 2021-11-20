package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
    public static final String EXTRA_REPLY = "com.example.android.taskmaster.REPLY";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = TaskDataBase.getInstance(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);


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
//        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        TextInputEditText title = findViewById(R.id.title);
        TextInputEditText desc = findViewById(R.id.desc);
//        Task task = new Task(Objects.requireNonNull(title.getText()).toString(), Objects.requireNonNull(desc.getText()).toString(), "new");
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                addList(task);
//            }
//        });
//
//        TextView textView = findViewById(R.id.totalTask);
//        String[] arrayOfText = textView.getText().toString().split(":");
//        String firstHalf = arrayOfText[0];
//        String textNumber = arrayOfText[1].split(" ")[1];
//        int number = Integer.parseInt(textNumber) + 1;
//        textView.setText(firstHalf + ": " + number);
        Intent intent = new Intent();
        intent.putExtra("title",Objects.requireNonNull(title.getText()).toString());
        intent.putExtra("desc",Objects.requireNonNull(desc.getText()).toString());
        setResult(RESULT_OK, intent);
        Toast.makeText(getApplicationContext(), "submitted!!", Toast.LENGTH_LONG).show();
        finish();
    }


    public static void addList (
            Task task) {
        db.taskDao().insertTask(task);
    }
}