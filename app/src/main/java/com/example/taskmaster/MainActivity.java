package com.example.taskmaster;


import androidx.annotation.NonNull;
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


import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileUser;
import com.amazonaws.services.pinpoint.model.DefaultPushNotificationMessage;
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
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static PinpointManager pinpointManager;

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", String.valueOf(userStateDetails.getUserState()));
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }


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
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }


        RecyclerView recyclerView = findViewById(R.id.mainTaskView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getPinpointManager(getApplicationContext());
        assignUser();

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
        String teamName = sharedPreferences.getString("team", "Anonymous Team");
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
                    Set<String> names = new HashSet<>();
                    for (Team team : response.getData()) {
                        names.add(team.getName());
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("teamNames", names);
                    editor.apply();
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );






    }

    public void assignUser () {
        SharedPreferences sharedPreferences  = getApplicationContext().getSharedPreferences("tasks", MODE_PRIVATE);
        String teamName = sharedPreferences.getString("team", "Anonymous Team");
        TargetingClient targetingClient = pinpointManager.getTargetingClient();
        EndpointProfile endpointProfile = targetingClient.currentEndpoint();
        EndpointProfileUser endpointProfileUser = new EndpointProfileUser();
        endpointProfileUser.setUserId(teamName);
        endpointProfile.setUser(endpointProfileUser);
        targetingClient.updateEndpointProfile(endpointProfile);
        Log.i(TAG, "Assign user Team" + endpointProfileUser.getUserId() + "To" + endpointProfile.getEndpointId());
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