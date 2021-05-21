package com.mact.simpleautomationapp.Room.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Email implements Parcelable {

    private String senderEmailId;
    private String senderPasswd;
    private String recipientEmailId;
    private String subject;
    private String emailBody;

    public Email(String senderEmailId, String senderPasswd, String recipientEmailId, String subject, String emailBody) {
        this.senderEmailId = senderEmailId;
        this.senderPasswd = senderPasswd;
        this.recipientEmailId = recipientEmailId;
        this.subject = subject;
        this.emailBody = emailBody;

    }

    protected Email(Parcel in) {
        senderEmailId = in.readString();
        senderPasswd = in.readString();
        recipientEmailId = in.readString();
        subject = in.readString();
        emailBody = in.readString();
    }

    public static final Creator<Email> CREATOR = new Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };

    public String getSenderEmailId() {
        return senderEmailId;
    }

    public void setSenderEmailId(String senderEmailId) {
        this.senderEmailId = senderEmailId;
    }

    public String getSenderPasswd() {
        return senderPasswd;
    }

    public void setSenderPasswd(String senderPasswd) {
        this.senderPasswd = senderPasswd;
    }

    public String getRecipientEmailId() {
        return recipientEmailId;
    }

    public void setRecipientEmailId(String recipientEmailId) {
        this.recipientEmailId = recipientEmailId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(senderEmailId);
        dest.writeString(senderPasswd);
        dest.writeString(recipientEmailId);
        dest.writeString(subject);
        dest.writeString(emailBody);
    }
}
