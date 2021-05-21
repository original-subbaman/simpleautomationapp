package com.mact.simpleautomationapp.Activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mact.simpleautomationapp.Fragments.ReviewAutomationFragment;
import com.mact.simpleautomationapp.Fragments.SelectActionFragment;
import com.mact.simpleautomationapp.Fragments.SelectTriggerFragment;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Entity.Email;
import com.mact.simpleautomationapp.Room.Entity.Trigger;
import com.mact.simpleautomationapp.Services.BroadcastManager;
import com.mact.simpleautomationapp.Utils.ActionTriggerConstants;
import com.mact.simpleautomationapp.databinding.ActivityAutomateAndroidBinding;

import java.text.DateFormat;

import static com.mact.simpleautomationapp.Utils.CustomDialog.TAG;

public class AutomateAndroid extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private ActivityAutomateAndroidBinding mBinding;
    public static final int SELECT_TRIGGER_FRAGMENT = 0;
    public static final int SELECT_ACTION_FRAGMENT = 1;
    public static final int REVIEW_FRAGMENT = 2;
    public static final int ACTIVITY_EMAIL_CODE = 9;
    private AndroidAuto auto;
    private int lastFragmentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAutomateAndroidBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        auto = new AndroidAuto();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFragment(lastFragmentNumber);
    }

    private void loadFragment(int fragmentNumber){

        FragmentManager manager = getSupportFragmentManager();
        switch(fragmentNumber){
            case SELECT_TRIGGER_FRAGMENT:
                manager.beginTransaction().setReorderingAllowed(true)
                        .add(R.id.fragment_container_view_tag, SelectTriggerFragment.class, null)
                        .commit();
                lastFragmentNumber = SELECT_TRIGGER_FRAGMENT;
                break;
            case REVIEW_FRAGMENT:
                manager.beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, ReviewAutomationFragment.class, null)
                        .commit();
                lastFragmentNumber = REVIEW_FRAGMENT;
                break;
        }

    }

    public void setToolBarTitle(String title){
        mBinding.toolbar.setTitle(title);
    }

    public void switchFragment(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(id){
            case SELECT_TRIGGER_FRAGMENT:
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, SelectTriggerFragment.class, null)
                        .commit();
                lastFragmentNumber = SELECT_TRIGGER_FRAGMENT;
                break;
            case SELECT_ACTION_FRAGMENT:
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, SelectActionFragment.class, null)
                        .commit();
                lastFragmentNumber = SELECT_ACTION_FRAGMENT;
                break;
            case REVIEW_FRAGMENT:
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, ReviewAutomationFragment.class, null)
                        .commitNow();
                lastFragmentNumber = REVIEW_FRAGMENT;
                break;
        }

    }

    public AndroidAuto getAuto(){
        return this.auto;
    }

    public void setAuto(AndroidAuto auto){
        this.auto = auto;
    }

    public void createEnableDisableDialog(int arrayResId, String description, int type, String broadcastAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Select")
                .setSingleChoiceItems(arrayResId, 0, null)
                .setPositiveButton("Select", (dialog, which) -> {
                    ListView listView = ((AlertDialog)dialog).getListView();
                    String state = listView.getAdapter().getItem(listView.getCheckedItemPosition()).toString();
                    if(type == SELECT_TRIGGER_FRAGMENT){
                        auto.setTrigger(createTrigger(broadcastAction, state, description));
                        switchFragment(AutomateAndroid.SELECT_ACTION_FRAGMENT);
                    }else if(type == SELECT_ACTION_FRAGMENT){
                        auto.setAction(createAction(broadcastAction, state, description));
                        switchFragment(AutomateAndroid.REVIEW_FRAGMENT);
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public Action createAction(String broadcastAction, String finalState, String description){
        Action action = new Action(description);
        switch(broadcastAction){
            case ActionTriggerConstants.BLUETOOTH_STATE:
                action.setBroadcastAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                if ((finalState.equals("Enabled"))) {
                    action.setFinalState(BluetoothAdapter.STATE_ON);
                } else {
                    action.setFinalState(BluetoothAdapter.STATE_OFF);
                }
                break;
            case ActionTriggerConstants.WIFI_STATE:
                action.setBroadcastAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                if((finalState.equals("Enabled"))){
                    action.setFinalState(WifiManager.WIFI_STATE_ENABLED);
                }else{
                    action.setFinalState(WifiManager.WIFI_STATE_DISABLED);
                }
                break;
            case ActionTriggerConstants.LAUNCH_APPLICATION:
                action.setBroadcastAction(Intent.ACTION_HEADSET_PLUG);
                action.setFinalState(1);
                break;
            case ActionTriggerConstants.TURN_FLASH_ON:
                action.setBroadcastAction(ActionTriggerConstants.TURN_FLASH_ON);
                action.setFinalState(1);
                break;
            case ActionTriggerConstants.TEXT_TO_SPEECH:
                action.setBroadcastAction(ActionTriggerConstants.TEXT_TO_SPEECH);
                break;

        }
        return action;
    }

    public Trigger createTrigger(String selectedItem, String state, String description){
        Trigger trigger = new Trigger(description);
        switch(selectedItem){
            case ActionTriggerConstants.BLUETOOTH_STATE:
                if(state.equals("Enabled")){
                    trigger.setActionThatTriggers(BluetoothAdapter.ACTION_STATE_CHANGED);
                    trigger.setStateOfAction(BluetoothAdapter.STATE_ON);
                }else{
                    trigger.setActionThatTriggers(BluetoothAdapter.ACTION_STATE_CHANGED);
                    trigger.setStateOfAction(BluetoothAdapter.STATE_OFF);
                }
                break;
            case ActionTriggerConstants.WIFI_STATE:
                if(state.equals("Enabled")){
                    trigger.setActionThatTriggers(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    trigger.setStateOfAction(WifiManager.WIFI_STATE_ENABLED);
                }else{
                    trigger.setActionThatTriggers(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    trigger.setStateOfAction(WifiManager.WIFI_STATE_DISABLED);
                }
                break;
            case ActionTriggerConstants.HEADSET_PLUGGED:
                trigger.setActionThatTriggers(Intent.ACTION_HEADSET_PLUG);
                trigger.setTriggerDescription(description);
                break;
        }
        return  trigger;
    }

    public int getOptionsForSelection(String selection){
        switch(selection){
            case "Launch Application":
                return 0;
            case "Flashlight On":
                return 1;
            case "Send Email":
                return 2;
        }
        return R.array.enable_disable;
    }

    @Override
    public void onBackPressed() {
        lastFragmentNumber -= 1;
        if(lastFragmentNumber < 0){
            this.finish();
        }else{
            switchFragment(lastFragmentNumber);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        startAlarm(c);
        Trigger trigger = new Trigger("Raise alarm at time: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
        trigger.setActionThatTriggers(ActionTriggerConstants.ALARM_RAISED);
        getAuto().setTrigger(trigger);
        switchFragment(SELECT_ACTION_FRAGMENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastManager.class);
        intent.setAction(ActionTriggerConstants.ALARM_RAISED);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_EMAIL_CODE) {
            if (resultCode == RESULT_OK) {
                Email email = data.getParcelableExtra("email");
                Action action = new Action();
                action.setBroadcastAction(ActionTriggerConstants.SEND_EMAIL);
                action.setEmail(email);
                action.setActionDescription(getResources().getString(R.string.send_email_description));
                auto.setAction(action);
                lastFragmentNumber = REVIEW_FRAGMENT;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        startAlarm(calendar);

        Trigger trigger = new Trigger("Raise alarm on date: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
        trigger.setActionThatTriggers(ActionTriggerConstants.ALARM_RAISED);
        getAuto().setTrigger(trigger);
        switchFragment(SELECT_ACTION_FRAGMENT);
    }
}