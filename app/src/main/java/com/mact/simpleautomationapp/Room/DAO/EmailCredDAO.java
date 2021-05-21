package com.mact.simpleautomationapp.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mact.simpleautomationapp.Room.Entity.EmailCred;

import java.util.List;

@Dao
public interface EmailCredDAO {

    @Insert
    void insert(EmailCred cred);

    @Delete
    void delete(EmailCred cred);

    @Update
    void update(EmailCred cred);

    @Query("SELECT * FROM email_cred_table")
    LiveData<List<EmailCred>> getAllEmailCred();

}
