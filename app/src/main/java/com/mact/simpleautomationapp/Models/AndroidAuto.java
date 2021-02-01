package com.mact.simpleautomationapp.Models;

public class AndroidAuto {
    private String mTitle;
    private String mTriggerDescription;
    private String mActionDescription;

    public AndroidAuto(String title, String triggerDescription, String actionDescription) {
        this.mTitle = title;
        this.mTriggerDescription = triggerDescription;
        this.mActionDescription = actionDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTriggerDescription() {
        return mTriggerDescription;
    }

    public void setTriggerDescription(String mTriggerDescription) {
        this.mTriggerDescription = mTriggerDescription;
    }

    public String getActionDescription() {
        return mActionDescription;
    }

    public void setActionDescription(String mActionDescription) {
        this.mActionDescription = mActionDescription;
    }
}
