package com.mact.simpleautomationapp.Utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.mact.simpleautomationapp.Room.Entity.Action;

public class RunAutomateTask {

    private Context context;

    public RunAutomateTask(Context context)
    {
        this.context = context;
    }
    public void run(Action autoAction){
        String action = autoAction.getBroadcastAction();
        int finalState = autoAction.getFinalState();
        Intent intent = autoAction.getLaunchAppIntent();
        switch(action){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                runBluetoothTask(finalState);
                break;
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                runWifiTask(finalState);
                break;
            case ActionTriggerConstants.LAUNCH_APPLICATION:
                launchApplication(intent);
                break;

        }
    }

    private void launchApplication(Intent intent){
        if(intent != null){
            context.startActivity(intent);
        }
    }

    private void runWifiTask(int state){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(state == WifiManager.WIFI_STATE_ENABLED){
            wifiManager.setWifiEnabled(true);
        }else{
            wifiManager.setWifiEnabled(false);
        }
    }

    private void runBluetoothTask(int state){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(state == BluetoothAdapter.STATE_ON){
            adapter.enable();
        }else{
            adapter.disable();
        }

    }


}
