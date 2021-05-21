package com.mact.simpleautomationapp.Room.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mact.simpleautomationapp.Room.DAO.EmailCredDAO;
import com.mact.simpleautomationapp.Room.Database.AutomatedTaskDatabase;
import com.mact.simpleautomationapp.Room.Entity.EmailCred;

import java.util.List;

public class EmailCredRepository {

    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int UPDATE = 2;

    private EmailCredDAO dao;
    private LiveData<List<EmailCred>> allEmailCred;
    public EmailCredRepository(Application application) {
        AutomatedTaskDatabase db = AutomatedTaskDatabase.getInstance(application);
        dao = db.credDAO();
        allEmailCred = dao.getAllEmailCred();
    }

    public void insert(EmailCred cred){
        RoomTask roomTask = new RoomTask(dao, INSERT, cred);
        new Thread(roomTask).start();
    }

    public void delete(EmailCred cred){
        RoomTask roomTask = new RoomTask(dao, DELETE, cred);
        new Thread(roomTask).start();
    }

    public void update(EmailCred cred){
        RoomTask roomTask = new RoomTask(dao, UPDATE, cred);
        new Thread(roomTask).start();
    }

    public LiveData<List<EmailCred>> getAllEmailCred(){
        return this.allEmailCred;
    }

    private static class RoomTask implements Runnable{
        private EmailCredDAO dao;
        private int operation;
        private EmailCred cred;
        private RoomTask(EmailCredDAO dao, int op, EmailCred cred){
            this.operation = op;
            this.cred = cred;
            this.dao = dao;
        }

        @Override
        public void run() {
            switch(operation){
                case INSERT:
                    dao.insert(cred);
                    break;
                case DELETE:
                    dao.delete(cred);
                    break;
                case UPDATE:
                    dao.update(cred);
                    break;
            }
        }
    }



}
