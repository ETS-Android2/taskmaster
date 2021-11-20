package com.example.taskmaster;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Task.class, version = 2)
public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public static volatile TaskDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static TaskDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskDataBase.class, "task").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
