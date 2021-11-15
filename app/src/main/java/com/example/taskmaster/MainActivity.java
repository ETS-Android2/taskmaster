package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = sharedPreferences.getString("name","Anonymous");
        System.out.println(name);
        TextView textView = (TextView) findViewById(R.id.mainUsername);
        textView.setText(name+"'s Tasks");
    }

    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);

    }

    public void showAddTask(View v) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void showSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    public void showTask(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
       Intent intent = new Intent(this, TaskDetail.class);
        switch (view.getId()) {
            case R.id.taskOne:
                editor.putString("title", "Task number one");
                editor.putString("desc", "Task one Lorem ipsum" + getString(R.string.descDetail).toString());
                break;
            case R.id.taskTwo:
                editor.putString("title", "Task number Two");
                editor.putString("desc", "Task Two Lorem ipsum" + getString(R.string.descDetail).toString());
                break;
            case R.id.taskThree:
                editor.putString("title", "Task number Three");
                editor.putString("desc", "Task Three Lorem ipsum" + getString(R.string.descDetail).toString());
                break;
            default:
                editor.putString("title", "No Title");
                editor.putString("desc", "No Desc Lorem ipsum" + getString(R.string.descDetail).toString());
                break;
        }
        editor.apply();
        startActivity(intent);
    }


}