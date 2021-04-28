package com.mact.simpleautomationapp.Room.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Room.DAO.AndroidAutoDAO;
import com.mact.simpleautomationapp.Room.Database.AutomatedTaskDatabase;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;

import java.util.List;

public class AndroidAutoRepository {

    public static final int INSERT = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;


    private AndroidAutoDAO dao;
    private LiveData<List<AndroidAuto>> allAndroidAutos;

    public AndroidAutoRepository(Application application) {
        AutomatedTaskDatabase db = AutomatedTaskDatabase.getInstance(application);
        dao = db.autoDAO();
        allAndroidAutos = dao.getAllAndroidAutos();
    }

    public void insert(AndroidAuto automateAndroid){
        RoomTask task = new RoomTask(dao, INSERT, automateAndroid);
        new Thread(task).start();
    }

    public void update(AndroidAuto automateAndroid){
        RoomTask task = new RoomTask(dao, UPDATE, automateAndroid);
        new Thread(task).start();
    }

    public void delete(AndroidAuto automateAndroid){
        RoomTask task = new RoomTask(dao, DELETE, automateAndroid);
        new Thread(task).start();
    }

    public LiveData<List<AndroidAuto>> getAllAndroidAutos() {
        return allAndroidAutos;
    }

    private static class RoomTask implements Runnable{
        private AndroidAutoDAO dao;
        private int operation;
        private AndroidAuto auto;
        private RoomTask(AndroidAutoDAO dao, int op, AndroidAuto auto){
            this.operation = op;
            this.auto = auto;
        }

        @Override
        public void run() {
            switch(operation){
                case INSERT:
                    dao.insert(auto);
                    break;
                case DELETE:
                    dao.delete(auto);
                    break;
                case UPDATE:
                    dao.update(auto);
                    break;
            }
        }
    }
}
