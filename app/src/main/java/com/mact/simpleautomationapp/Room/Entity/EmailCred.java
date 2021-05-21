package com.mact.simpleautomationapp.Room.Entity;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "email_cred_table")
public class EmailCred {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String emailID;
    private String passwd;

    public EmailCred(String emailID, String passwd) {
        this.emailID = emailID;
        this.passwd = passwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Ignore
    @Override
    public String toString() {
        return this.emailID;
    }
}
