package com.mact.simpleautomationapp.Models;

public class ListItem {
    private int imageResId;
    private String text;

    public ListItem(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
