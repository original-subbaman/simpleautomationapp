package com.mact.simpleautomationapp.Room.Entity;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "android_auto_table")
public class AndroidAuto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mTitle;
    private Intent mLaunchIntent;
    private Trigger mTrigger;
    private Action mAction;

    public AndroidAuto(String mTitle, Intent mLaunchIntent, Trigger mTrigger, Action mAction) {
        this.mTitle = mTitle;
        this.mLaunchIntent = mLaunchIntent;
        this.mTrigger = mTrigger;
        this.mAction = mAction;
    }

    @Ignore
    public AndroidAuto(String title) {
        this.mTitle = title;
    }

    public AndroidAuto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setLaunchIntent(Intent intent){
        this.mLaunchIntent = intent;
    }

    public Intent getLaunchIntent(){
        return this.mLaunchIntent;
    }

    public Trigger getTrigger() {
        return mTrigger;
    }

    public void setTrigger(Trigger trigger){
        this.mTrigger = trigger;
    }

    public Action getAction(){ return this.mAction; }

    public void setAction(Action action) { this.mAction = action; }
}
