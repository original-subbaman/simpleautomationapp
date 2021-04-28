package com.mact.simpleautomationapp.Services;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Utils.RunAutomateTask;

import java.util.ArrayList;
import java.util.List;

public class BroadcastManager extends BroadcastReceiver {
    private static List<AndroidAuto> androidAutoList = new ArrayList<>();
    private static RunAutomateTask automateTask;
    public static final String TAG = "BroadcastManager";
    private Context context;

    public BroadcastManager(){}

    public BroadcastManager(Context  context){
        this.context = context;
        automateTask = new RunAutomateTask(context);
    }

    public static void addAuto(AndroidAuto auto){
        androidAutoList.add(auto);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AndroidAuto androidAuto = getAndroidAuto(action);
        if(androidAuto == null) return;
        switch(action){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                /*if(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_ON){
                    for(AndroidAuto auto : androidAutoList){
                        if(auto.getTriggerDescription().contains("Bluetooth")){
                            Intent launchIntent = auto.getLaunchIntent();
                            context.startActivity(launchIntent);
                        }
                    }
                }*/
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                //if the state of the bluetooth is equal to the state of the trigger
                if(adapter.getState() == androidAuto.getTrigger().getStateOfAction()){
                    automateTask.run(androidAuto.getAction());
                }
                break;
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                 WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                 if(wifiManager.getWifiState() == androidAuto.getTrigger().getStateOfAction()){
                     automateTask.run(androidAuto.getAction());
                 }
                break;
            default:
                automateTask.run(androidAuto.getAction());
        }
    }

    /*
    Return the AndroidAuto object whose intent's action matches the action of the Intent received by the onReceive method
     */
    private AndroidAuto getAndroidAuto(String action){
        for(AndroidAuto auto : androidAutoList){
            Log.d(TAG, "getAndroidAuto: " + auto.getTrigger().getActionThatTriggers());
            if(auto.getTrigger().getActionThatTriggers().equals(action)){
                Log.d(TAG, "getAndroidAuto: found");
                return auto;
            }
        }
        return null;
    }






}
