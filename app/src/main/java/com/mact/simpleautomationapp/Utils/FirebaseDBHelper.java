package com.mact.simpleautomationapp.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBHelper {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public FirebaseDBHelper(){
        db = FirebaseDatabase.getInstance();
    }

    public void setValue(int value, String ref){
        dbRef = db.getReference(ref);
        dbRef.setValue(value);
    }

    public FirebaseDatabase getDb(){
        return this.db;
    }

}
