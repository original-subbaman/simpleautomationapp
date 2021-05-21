package com.mact.simpleautomationapp.Room.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Trigger implements Parcelable {
    private String mActionThatTriggers;
    private int mStateOfAction;
    private String mTriggerDescription;
    private String mSmsSenderName;

    public Trigger(String description){
        this.mTriggerDescription = description;
    }

    public Trigger(String actionToTrigger, int stateOfAction) {
        this.mActionThatTriggers = actionToTrigger;
        this.mStateOfAction = stateOfAction;
    }

    public Trigger(){

    }

    protected Trigger(Parcel in) {
        mActionThatTriggers = in.readString();
        mStateOfAction = in.readInt();
        mTriggerDescription = in.readString();
        mSmsSenderName = in.readString();
    }

    public static final Creator<Trigger> CREATOR = new Creator<Trigger>() {
        @Override
        public Trigger createFromParcel(Parcel in) {
            return new Trigger(in);
        }

        @Override
        public Trigger[] newArray(int size) {
            return new Trigger[size];
        }
    };

    public String getActionThatTriggers() {
        return mActionThatTriggers;
    }

    public void setActionThatTriggers(String mActionToTrigger) {
        this.mActionThatTriggers = mActionToTrigger;
    }

    public int getStateOfAction() {
        return mStateOfAction;
    }

    public void setStateOfAction(int mStateOfAction) {
        this.mStateOfAction = mStateOfAction;
    }

    public String getTriggerDescription(){
        return this.mTriggerDescription;
    }

    public void setTriggerDescription(String description){
        this.mTriggerDescription = description;
    }

    public String getSmsSenderName() {
        return mSmsSenderName;
    }

    public void setSmsSenderName(String name) {
        this.mSmsSenderName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mActionThatTriggers);
        dest.writeInt(mStateOfAction);
        dest.writeString(mTriggerDescription);
        dest.writeString(mSmsSenderName);
    }
}
