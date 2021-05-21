package com.mact.simpleautomationapp.Services;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import com.mact.simpleautomationapp.Activities.App;
import com.mact.simpleautomationapp.Activities.MainActivity;
import com.mact.simpleautomationapp.Adapters.AutosRecyclerViewAdapter;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;

import java.util.ArrayList;

public class BroadcastReceiverService extends Service {

    private BroadcastManager broadcastManager;
    public static final String TAG = "Broadcast Service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        ArrayList<AndroidAuto> tasks = intent.getParcelableArrayListExtra("androidAutos");
        broadcastManager = new BroadcastManager(getApplicationContext(), tasks);
        registerReceiver(broadcastManager, getIntentFilters());
        return START_STICKY_COMPATIBILITY;
    }

    private void createNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.title))
                .setContentText(getResources().getString(R.string.notification_description))
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(1, builder.build());

    }


    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastManager);
    }

    private IntentFilter getIntentFilters(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);

        return intentFilter;
    }
}
