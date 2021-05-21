package com.mact.simpleautomationapp.Room.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.mact.simpleautomationapp.Room.Entity.Trigger;

public class TriggerTypeConverter {

    @TypeConverter
    public String fromObject(Trigger trigger){
        return new Gson().toJson(trigger);
    }

    @TypeConverter
    public Trigger fromString(String value){
        Gson gson = new Gson();
        Trigger trigger = gson.fromJson(value, Trigger.class);
        return trigger;
    }

}
