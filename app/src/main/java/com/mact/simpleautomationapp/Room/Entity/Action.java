package com.mact.simpleautomationapp.Room.Entity;

import android.content.Intent;

public class Action {
    private String mBroadcastAction;
    private int mFinalState;
    private String mActionDescription;
    private Intent mLaunchAppIntent;

    public Action(String description){
        this.mActionDescription = description;
    }
    public Action(String broadcastAction, int stateToReach) {
        this.mBroadcastAction = broadcastAction;
        this.mFinalState = stateToReach;
    }

    public Action(){}

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

}
