package com.example.taskmaster;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public static volatile TaskDataBase db;

    public static TaskDataBase getInstance(final Context context) {
        if (db == null) db = Room.databaseBuilder(context.getApplicationContext(), TaskDataBase.class, "task").build();
        return db;
    }
}
