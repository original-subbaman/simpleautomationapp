package com.mact.simpleautomationapp.Room.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mact.simpleautomationapp.Room.DAO.AndroidAutoDAO;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;

@Database(entities = {AndroidAuto.class}, version = 1)
public abstract class AutomatedTaskDatabase extends RoomDatabase {
    private static AutomatedTaskDatabase instance;

    public abstract AndroidAutoDAO autoDAO();

    public static synchronized AutomatedTaskDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AutomatedTaskDatabase.class,
                    "automated_task_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
