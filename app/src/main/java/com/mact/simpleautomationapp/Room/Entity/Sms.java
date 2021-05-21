package com.mact.simpleautomationapp.Room.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Sms implements Parcelable {
    private String senderName;
    private String message;

    public Sms(String senderName) {
        this.senderName = senderName;
    }

    protected Sms(Parcel in) {
        senderName = in.readString();
        message = in.readString();
    }

    public static final Creator<Sms> CREATOR = new Creator<Sms>() {
        @Override
        public Sms createFromParcel(Parcel in) {
            return new Sms(in);
        }

        @Override
        public Sms[] newArray(int size) {
            return new Sms[size];
        }
    };

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(senderName);
        dest.writeString(message);
    }
}
