package com.example.taskmaster;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> gitAll();

    @Query("SELECT * FROM tasks WHERE uid = (:uid) ")
    LiveData<Task> findById(int uid);

    @Insert
    void insertTask(Task task);

    @Delete
    void Delete(Task task);
}
