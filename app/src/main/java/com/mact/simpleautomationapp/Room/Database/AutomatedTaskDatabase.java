package com.mact.simpleautomationapp.Room.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mact.simpleautomationapp.Room.Converters.ActionTypeConverter;
import com.mact.simpleautomationapp.Room.Converters.TriggerTypeConverter;
import com.mact.simpleautomationapp.Room.DAO.AndroidAutoDAO;
import com.mact.simpleautomationapp.Room.DAO.EmailCredDAO;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Entity.EmailCred;

@Database(entities = {AndroidAuto.class, EmailCred.class}, version = 1, exportSchema = false)
@TypeConverters({ActionTypeConverter.class, TriggerTypeConverter.class})
public abstract class AutomatedTaskDatabase extends RoomDatabase {
    private static AutomatedTaskDatabase instance;

    public abstract AndroidAutoDAO autoDAO();
    public abstract EmailCredDAO credDAO();

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
