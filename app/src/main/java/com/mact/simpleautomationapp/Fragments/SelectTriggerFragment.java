package com.mact.simpleautomationapp.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Adapters.TriggerActionRecyclerViewAdapter;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.Sms;
import com.mact.simpleautomationapp.Room.Entity.Trigger;
import com.mact.simpleautomationapp.Utils.ActionTriggerConstants;
import com.mact.simpleautomationapp.Utils.ContactRetrieval;
import com.mact.simpleautomationapp.Utils.CustomDialog;
import com.mact.simpleautomationapp.Utils.CustomDialogContract;
import com.mact.simpleautomationapp.Utils.CustomLoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectTriggerFragment extends Fragment {

    private ArrayList<String> items;
    private RecyclerView triggerRecyclerView;
    private TriggerActionRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private AutomateAndroid automateAndroid;
    private final int SMS_RECEIVE_READ_PERMISSION = 1;
    private static final int TRIGGER_ID = R.array.triggers;
    private Handler handler;

    public SelectTriggerFragment(){
        super(R.layout.fragment_trigger_android);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapterDataSet(TRIGGER_ID);
        automateAndroid = (AutomateAndroid) getActivity();
        adapter = new TriggerActionRecyclerViewAdapter(items);

        adapter.setOnItemClickListener(position -> {
            handleAdapterItemClick(position);

        });
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    private void handleAdapterItemClick(int position){
        String selectedItem = items.get(position);

        switch(selectedItem){
            case ActionTriggerConstants
                    .ALARM_RAISED:
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(automateAndroid.getSupportFragmentManager(), "timePicker");
                break;
            case ActionTriggerConstants.DATE:
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(automateAndroid.getSupportFragmentManager(), "datePicker");
                break;
            case ActionTriggerConstants.SMS_RECEIVED:
                if(!ContactRetrieval.isSmsPermissionGranted(getActivity())){
                    requestSmsPermission();
                }else{
                    automateAndroid.getAuto().setTrigger(createSmsTrigger());
                }
                break;
            default:
                int arrayResId = automateAndroid.getOptionsForSelection(items.get(position));
                automateAndroid.createEnableDisableDialog(arrayResId, items.get(position), 0, items.get(position));
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AutomateAndroid) getActivity()).setToolBarTitle("Select Trigger");
        View view = inflater.inflate(R.layout.fragment_trigger_android, container, false);
        ((TextView) view.findViewById(R.id.action_trigger_description)).setText(R.string.trigger_explain);
        triggerRecyclerView = view.findViewById(R.id.recycler_view_tag);
        triggerRecyclerView.setHasFixedSize(true);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void setAdapterDataSet(int resId){
        items = new ArrayList<>(
                Arrays.asList(getContext().getResources().getStringArray(resId))
        );
    }



    private void requestSmsPermission(){
        String[] permission_list = new String[3];
        permission_list[0] = Manifest.permission.RECEIVE_SMS;
        permission_list[1] = Manifest.permission.READ_SMS;
        permission_list[2] = Manifest.permission.READ_CONTACTS;
        requestPermissions(permission_list, SMS_RECEIVE_READ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SMS_RECEIVE_READ_PERMISSION){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                automateAndroid.getAuto().setTrigger(createSmsTrigger());
            }else{
                Toast.makeText(getActivity(), "Permission denied. Cannot create Sms trigger.", Toast.LENGTH_LONG).show();
            }
        }

    }

    private Trigger showContactList(List<String> contactNames){
        Trigger trigger = new Trigger(getResources().getString(R.string.sms_received_description));

        String[] names = contactNames.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setTitle("Select a contact name")
                .setSingleChoiceItems(names, 0, null)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView listView = ((AlertDialog)dialog).getListView();
                        String selectedName = listView.getAdapter().getItem(listView.getCheckedItemPosition()).toString();
                        String number = ContactRetrieval.getContactNumber(selectedName, getActivity());
                        trigger.setActionThatTriggers(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
                        trigger.setSmsSenderName(number);
                        Log.d("SelectTrigger", "onClick: " + trigger.getSmsSenderName());
                        automateAndroid.switchFragment(AutomateAndroid.SELECT_ACTION_FRAGMENT);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

        return trigger;
    }




    private Trigger createSmsTrigger(){
        List<String> contactNames = ContactRetrieval.getContactList(getActivity());
        Trigger trigger = showContactList(contactNames);
        return trigger;
    }
}
