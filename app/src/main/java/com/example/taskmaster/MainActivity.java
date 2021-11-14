package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);

    }

    public void showAddTask(View v) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }


}