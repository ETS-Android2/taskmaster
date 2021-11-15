package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("tasks", MODE_PRIVATE);
        TextView textViewTitle = findViewById(R.id.titleDetail);
        TextView textViewDesc = findViewById(R.id.descDetail);
        textViewTitle.setText(sharedPreferences.getString("title", "No title"));
        textViewDesc.setText(sharedPreferences.getString("desc", "No Desc"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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