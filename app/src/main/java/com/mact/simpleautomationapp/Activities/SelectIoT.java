package com.mact.simpleautomationapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mact.simpleautomationapp.Adapters.IoTRecyclerAdapter;
import com.mact.simpleautomationapp.Models.IoTDevice;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Utils.FirebaseDBHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectIoT extends AppCompatActivity {
    public static final String TAG = "SelectIoT";
    private RecyclerView recyclerView;
    private IoTRecyclerAdapter adapter;
    private List<IoTDevice> iotDevices;
    private FirebaseDBHelper firebaseDBHelper;
    private DatabaseReference fanRef;
    private DatabaseReference ledOne;
    private DatabaseReference ledTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_io_t);
        setSupportActionBar(((Toolbar) findViewById(R.id.iot_toolbar)));
        recyclerView = findViewById(R.id.iot_recycler_view);
        iotDevices = new ArrayList<>();
        iotDevices.add(new IoTDevice(0, "fan"));
        iotDevices.add(new IoTDevice(0, "ledOne"));
        iotDevices.add(new IoTDevice(0, "ledTwo"));
        firebaseDBHelper = new FirebaseDBHelper();
        setUpFirebaseReference();
        setUpRecyclerView();
        setDBListener();
    }
    private void setUpFirebaseReference(){
        fanRef = firebaseDBHelper.getDb().getReference("fan");
        ledOne = firebaseDBHelper.getDb().getReference("ledOne");
        ledTwo = firebaseDBHelper.getDb().getReference("ledTwo");
    }

    private void setDBListener(){
        fanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addToDataSet(dataSnapshot.getValue(Integer.class), dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
        ledOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addToDataSet(dataSnapshot.getValue(Integer.class), dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());

            }
        });
        ledTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addToDataSet(dataSnapshot.getValue(Integer.class), dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());

            }
        });
    }

    public void addToDataSet(int value, String deviceName){
        IoTDevice device = new IoTDevice(value, deviceName);
        if(!adapter.getIotDevices().contains(device)){
            adapter.getIotDevices().add(device);
            adapter.notifyDataSetChanged();
        }

    }

    public void modifyValue(int value, int pos){
        String key = adapter.getIotDevices().get(pos).getDeviceName();
        if(key.equals("fan")){
            int fanVal = (value == 1) ? 0 : 1;
            adapter.getIotDevices().get(pos).setValue(fanVal);
            firebaseDBHelper.setValue(fanVal, key);

        }else{
            adapter.getIotDevices().get(pos).setValue(value);
            firebaseDBHelper.setValue(value, key);
        }
    }

    private void setUpRecyclerView(){
        adapter = new IoTRecyclerAdapter(iotDevices, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}