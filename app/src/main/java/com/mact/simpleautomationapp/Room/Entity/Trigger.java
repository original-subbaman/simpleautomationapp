package com.mact.simpleautomationapp.Room.Entity;

public class Trigger {
    private String mActionThatTriggers;
    private int mStateOfAction;
    private String mTriggerDescription;

    public Trigger(String description){
        this.mTriggerDescription = description;
    }
    public Trigger(String actionToTrigger, int stateOfAction) {
        this.mActionThatTriggers = actionToTrigger;
        this.mStateOfAction = stateOfAction;
    }

    public Trigger(){

    }

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
}
