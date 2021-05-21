package com.mact.simpleautomationapp.Room.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mact.simpleautomationapp.Room.Entity.EmailCred;
import com.mact.simpleautomationapp.Room.Repository.EmailCredRepository;

import java.util.List;

public class EmailCredViewModel extends AndroidViewModel {
    private EmailCredRepository repo;
    private LiveData<List<EmailCred>> allEmailCred;


    public EmailCredViewModel(@NonNull Application application) {
        super(application);
        repo = new EmailCredRepository(application);
        allEmailCred = repo.getAllEmailCred();
    }

    public void insert(EmailCred cred){
        repo.insert(cred);
    }

    public void delete(EmailCred cred){
        repo.delete(cred);
    }

    public void update(EmailCred cred){
        repo.update(cred);
    }

    public LiveData<List<EmailCred>> getAllEmailCred(){
        return this.allEmailCred;
    }

}
