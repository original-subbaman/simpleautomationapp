package com.mact.simpleautomationapp.Services;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Entity.Trigger;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;
import com.mact.simpleautomationapp.Utils.RunAutomateTask;

import java.util.ArrayList;
import java.util.List;

public class BroadcastManager extends BroadcastReceiver {
    private static List<AndroidAuto> androidAutoList;
    private static RunAutomateTask automateTask;
    public static final String TAG = "BroadcastManager";
    private Context context;

    public BroadcastManager(Context  context, ArrayList<AndroidAuto> tasks){
        this.context = context;
        automateTask = new RunAutomateTask(context);
        androidAutoList = new ArrayList<>(tasks);
    }


    public BroadcastManager() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String trigger = intent.getAction();
        Log.d(TAG, "onReceive: action " + trigger);
        AndroidAuto androidAuto = getAndroidAuto(trigger);
        if(androidAuto == null) return;
        switch(trigger){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
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
            case Telephony.Sms.Intents.SMS_RECEIVED_ACTION:
                SmsMessage[] rawSmsMsg;
                rawSmsMsg = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                for(SmsMessage message : rawSmsMsg){
                    if(message != null){
                        String sender = message.getDisplayOriginatingAddress();
                        Log.d(TAG, "onReceive: " + sender);
                        Trigger autoTrigger = androidAuto.getTrigger();
                        if(sender.toLowerCase().equals(autoTrigger.getSmsSenderName().toLowerCase())){
                            Action action = androidAuto.getAction();
                            action.setMessage(message.getDisplayMessageBody());
                            automateTask.run(action);
                        }
                    }
                }

                //getSMSText();
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
            if(auto.getTrigger().getActionThatTriggers().equals(action)){
                return auto;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getSMSText(){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if(cursor.moveToFirst()){
            Log.d(TAG, "getSMSText: " + cursor.getString(2));
        }
        return null;
    }
}
