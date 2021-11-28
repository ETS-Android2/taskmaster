package com.example.taskmaster;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.widget.TextView;


import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.ApiOperation;
import com.amplifyframework.api.aws.AWSApiPlugin;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.async.Cancelable;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    // old code for room
    // private TaskViewModel taskViewModel;
//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK) {
//                        assert result.getData() != null;
//                        Task task = new Task(result.getData().getStringExtra("title"), result.getData().getStringExtra("desc"), "new");
//                        taskViewModel.insert(task);
//                    }
//                }
//            }
//    );
    List<Task> tasks = new ArrayList<>();
    TaskAdapter adapter = new TaskAdapter(tasks);
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }


        RecyclerView recyclerView = findViewById(R.id.mainTaskView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);






        // Old code for room
        // taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        // Observer<List<Task>> observer = new Observer<List<Task>>() {
        //  @Override
        //  public void onChanged(List<Task> tasks) {
        //  recyclerView.setAdapter(new TaskAdapter(tasks));
        //  }
        // };
        // taskViewModel.getAllTasks().observe(this, observer);

    }

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Anonymous");
        String teamName = sharedPreferences.getString("team","Anonymous Team");
        TextView nameView = findViewById(R.id.mainUsername);
        TextView teamView = findViewById(R.id.mainTeam);
        nameView.setText(name + "'s Tasks");
        teamView.setText(teamName);
        Amplify.API.query(
                ModelQuery.list(Task.class, Task.TEAM_NAME.contains(teamName)),
                response -> {
                    tasks.clear();
                    for (Task task : response.getData()) {
                        tasks.add(task);
                    }
                    handler.post(runnable);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    Set<String> names  = new HashSet<>();
                    for (Team team : response.getData()) {
                        names.add(team.getName());
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("teamNames",names);
                    editor.apply();
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );



    }


    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);


    }

    public void showAddTask(View v) {
        Intent intent = new Intent(this, AddTask.class);
        intent.putExtra("count", tasks.size());
        // for room to lunch activity with save
//        activityResultLauncher.launch(intent);
        startActivity(intent);

    }

    public void showSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }
}