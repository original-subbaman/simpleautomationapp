package com.mact.simpleautomationapp.Room.Entity;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class Action implements Parcelable {
    private String mBroadcastAction;
    private int mFinalState;
    private String mActionDescription;
    private Intent mLaunchAppIntent;
    private Email mEmail;
    private String mMessage;

    public Action(String description){
        this.mActionDescription = description;
    }

    public Action(String broadcastAction, int stateToReach) {
        this.mBroadcastAction = broadcastAction;
        this.mFinalState = stateToReach;
    }

    public Action(){}

    protected Action(Parcel in) {
        mBroadcastAction = in.readString();
        mFinalState = in.readInt();
        mActionDescription = in.readString();
        mLaunchAppIntent = in.readParcelable(Intent.class.getClassLoader());
        mEmail = in.readParcelable(Email.class.getClassLoader());
        mMessage = in.readString();
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };

    public String getBroadcastAction() {
        return mBroadcastAction;
    }

    public void setBroadcastAction(String mBroadcastAction) {
        this.mBroadcastAction = mBroadcastAction;
    }

    public void setLaunchAppIntent(Intent intent){
        this.mLaunchAppIntent = intent;
    }

    public Intent getLaunchAppIntent(){
        return this.mLaunchAppIntent;
    }

    public int getFinalState() {
        return mFinalState;
    }

    public void setFinalState(int mStateToReach) {
        this.mFinalState = mStateToReach;
    }

    public String getActionDescription(){
        return this.mActionDescription;
    }

    public void setActionDescription(String description){
        this.mActionDescription = description;
    }

    public Email getEmail() {
        return mEmail;
    }

    public void setEmail(Email mEmail) {
        this.mEmail = mEmail;
    }

    public String getMessage(){
        return this.mMessage;
    }

    public void setMessage(String message){
        this.mMessage = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBroadcastAction);
        dest.writeInt(mFinalState);
        dest.writeString(mActionDescription);
        dest.writeParcelable(mLaunchAppIntent, flags);
        dest.writeParcelable(mEmail, flags);
        dest.writeString(mMessage);
    }
}
