package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class AddTask extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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


    @SuppressLint("SetTextI18n")
    public void addTask(View view) {
        TextInputEditText title = findViewById(R.id.title);
        TextInputEditText desc = findViewById(R.id.desc);
        Task task = new Task(Objects.requireNonNull(title.getText()).toString(), Objects.requireNonNull(desc.getText()).toString());
        TextView textView = findViewById(R.id.totalTask);
        String[] arrayOfText = textView.getText().toString().split(":");
        String firstHalf = arrayOfText[0];
        String textNumber = arrayOfText[1].split(" ")[1];
//        System.out.println(Integer.parseInt(textNumber)+5);
        int number = Integer.parseInt(textNumber) + 1;
        textView.setText(firstHalf + ": " + number);
        Toast.makeText(getApplicationContext(), "submitted!!", Toast.LENGTH_LONG).show();
    }
}