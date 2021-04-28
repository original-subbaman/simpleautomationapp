package com.mact.simpleautomationapp.Room.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Repository.AndroidAutoRepository;

import java.util.List;

public class AutomatedTaskViewModel extends AndroidViewModel {
    private AndroidAutoRepository androidRepo;
    private LiveData<List<AndroidAuto>> allAndroidTasks;

    public AutomatedTaskViewModel(@NonNull Application application) {
        super(application);
        androidRepo = new AndroidAutoRepository(application);
        allAndroidTasks = androidRepo.getAllAndroidAutos();
    }

    public void insert(AndroidAuto auto){
        androidRepo.insert(auto);
    }

    public void update(AndroidAuto auto){
        androidRepo.update(auto);
    }

    public void delete(AndroidAuto auto){
        androidRepo.delete(auto);
    }

    public LiveData<List<AndroidAuto>> getAllAndroidTasks(){
        return allAndroidTasks;
    }

}
