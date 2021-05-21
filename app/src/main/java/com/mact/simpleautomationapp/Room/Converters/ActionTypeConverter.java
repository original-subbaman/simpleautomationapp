package com.mact.simpleautomationapp.Room.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.mact.simpleautomationapp.Room.Entity.Action;

public class ActionTypeConverter {

    @TypeConverter
    public String fromObject(Action action){
        return new Gson().toJson(action);
    }

    @TypeConverter
    public Action fromString(String value){
        Gson gson = new Gson();
        Action auto = gson.fromJson(value, Action.class);
        return auto;
    }

}
