package com.mact.simpleautomationapp.Room.Entity;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "android_auto_table")
public class AndroidAuto implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mTitle;
    private Trigger mTrigger;
    private Action mAction;

    public AndroidAuto(String mTitle, Trigger mTrigger, Action mAction) {
        this.mTitle = mTitle;
        this.mTrigger = mTrigger;
        this.mAction = mAction;
    }

    @Ignore
    public AndroidAuto(String title) {
        this.mTitle = title;
    }

    @Ignore
    public AndroidAuto(){}

    @Ignore
    protected AndroidAuto(Parcel in) {
        id = in.readInt();
        mTitle = in.readString();
        mTrigger = in.readParcelable(Trigger.class.getClassLoader());
        mAction = in.readParcelable(Action.class.getClassLoader());
    }

    @Ignore
    public static final Creator<AndroidAuto> CREATOR = new Creator<AndroidAuto>() {
        @Override
        public AndroidAuto createFromParcel(Parcel in) {
            return new AndroidAuto(in);
        }

        @Override
        public AndroidAuto[] newArray(int size) {
            return new AndroidAuto[size];
        }
    };

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


    public Trigger getTrigger() {
        return mTrigger;
    }

    public void setTrigger(Trigger trigger){
        this.mTrigger = trigger;
    }

    public Action getAction(){ return this.mAction; }

    public void setAction(Action action) { this.mAction = action; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mTitle);
        dest.writeParcelable(mTrigger, flags);
        dest.writeParcelable(mAction, flags);
    }
}
