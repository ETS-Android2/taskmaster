package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Setting extends AppCompatActivity {

    AutoCompleteTextView menuView;
    //    TextInputEditText nameView;
    Button login;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
        Set<String> teamNames = sharedPreferences.getStringSet("teamNames", new HashSet<>());
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String name = sharedPreferences.getString("name","Anonymous");
        String team = sharedPreferences.getString("team", "Please Select Your Team");
//        nameView = findViewById(R.id.name);
        menuView = findViewById(R.id.menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, new ArrayList<>(teamNames));
        menuView.setAdapter(adapter);
        menuView.setInputType(InputType.TYPE_NULL);
        menuView.setText(team, false);
        Amplify.Auth.fetchAuthSession(
                result -> {
                    editor.putBoolean("isSignedIn", result.isSignedIn());
                    editor.apply();
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


//        nameView.setText(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks",MODE_PRIVATE);
        boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn",false);

        login = findViewById(R.id.login);
        logOut = findViewById(R.id.logOut);

        if (isSignedIn) {
            login.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.GONE);
        }
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


    public void addName(View view) {


//        String name = Objects.requireNonNull(nameView.getText()).toString();
        String team = menuView.getText().toString();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn", false);
        String name = isSignedIn ? Amplify.Auth.getCurrentUser().getUsername() : "Anonymous";
        editor.putString("name", name);
        editor.putString("team", team);
        editor.apply();
        Toast.makeText(this, "User name changed to " + name + "And the team is " + team, Toast.LENGTH_LONG).show();
        finish();
    }


    public void signIn(View v) {

        Amplify.Auth.signInWithWebUI(
                this,
                result -> {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
                    boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn", false);
                    String name = isSignedIn ? Amplify.Auth.getCurrentUser().getUsername() : "Anonymous";
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.apply();
                    finish();
                },
                error -> Log.e("AuthQuickStart", error.toString())
        );
    }

    public void signOut(View v) {
        Amplify.Auth.signOut(
                () -> {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
                    boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn", false);
                    String name = isSignedIn ? Amplify.Auth.getCurrentUser().getUsername() : "Anonymous";
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.apply();
                    finish();
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }
}