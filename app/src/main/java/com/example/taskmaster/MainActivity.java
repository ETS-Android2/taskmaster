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

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.async.Cancelable;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;
import com.amplifyframework.datastore.generated.model.Task;


import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity {

//    private TaskViewModel taskViewModel;
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


    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        List<Task> tasks = new ArrayList<>();
        TaskAdapter adapter = new TaskAdapter(tasks);
        RecyclerView recyclerView = findViewById(R.id.mainTaskView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        Amplify.API.query(
//                ModelQuery.list(Task.class),
//                response -> {
//                    List<Task> temp = new ArrayList<>();
//                    for (Task task : response.getData()) {
//                        temp.add(task);
//                        Log.i("MyAmplifyApp", task.getTitle());
//                    }
//                    recyclerView.setAdapter(new TaskAdapter(temp));
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );

//        Amplify.DataStore.observe(Task.class,
//                cancelable -> Log.i("MyAmplifyApp", "Observation began."),
//                postChanged -> {
//                    Task post = postChanged.item();
//                    Log.i("MyAmplifyApp", "Post: " + post);
//                },
//                failure -> Log.e("MyAmplifyApp", "Observation failed.", failure),
//                () -> Log.i("MyAmplifyApp", "Observation complete.")
//        );

        ApiOperation subscription = Amplify.API.subscribe(
                ModelSubscription.onCreate(Task.class),
                onEstablished -> Log.i("ApiQuickStart", "Subscription established"),
                onCreated -> Log.i("ApiQuickStart", "Todo create subscription received: " + ((Task) onCreated.getData()).getDesc()),
                onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure),
                () -> Log.i("ApiQuickStart", "Subscription completed")
        );









//        Observer<List<Task>> observer = new Observer<List<Task>>() {
//            @Override
//            public void onChanged(List<Task> tasks) {
//                recyclerView.setAdapter(new TaskAdapter(tasks));
//            }
//        };
//        taskViewModel.getAllTasks().observe(this, observer);

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Anonymous");
        System.out.println(name);
        TextView textView =  findViewById(R.id.mainUsername);
        textView.setText(name + "'s Tasks");




    }


    public void showAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);


    }

    public void showAddTask(View v) {
        Intent intent = new Intent(this, AddTask.class);
//        intent.putExtra("count",taskViewModel.getAllTasks().getValue().size());
//        activityResultLauncher.launch(intent);
        startActivity(intent);

    }

    public void showSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }
}