package com.mact.simpleautomationapp.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;

import java.util.List;

@Dao
public interface AndroidAutoDAO {
    @Insert
    void insert(AndroidAuto auto);

    @Delete
    void delete(AndroidAuto auto);

    @Update
    void update(AndroidAuto auto);

    @Query("SELECT * FROM android_auto_table")
    LiveData<List<AndroidAuto>> getAllAndroidTasks();

    @Query("SELECT * FROM android_auto_table")
    List<AndroidAuto> getAllAndroidTasksAsList();
}
