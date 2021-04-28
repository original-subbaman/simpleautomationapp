package com.mact.simpleautomationapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Adapters.TriggerActionRecyclerViewAdapter;
import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Utils.ActionTriggerConstants;
import com.mact.simpleautomationapp.Utils.CustomDialogContract;
import com.mact.simpleautomationapp.Utils.CustomLoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SelectActionFragment extends Fragment implements CustomDialogContract {

    private ArrayList<String> items;
    private List<String> installedApps;
    private HashMap<String, Intent> labelIntentMap;
    private RecyclerView triggerRecyclerView;
    private TriggerActionRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private PackageManager packageManager;
    private AutomateAndroid automateAndroid;
    private Handler handler;
    private static final int ACTION_ID = R.array.actions;
    private static final int SELECTION_LAUNCH_APPLICATION = -1;

    public SelectActionFragment(){
        super(R.layout.fragment_trigger_android);
        installedApps = new ArrayList<>();
        handler = new Handler();
        labelIntentMap = new HashMap<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapterDataSet(ACTION_ID);
        automateAndroid = (AutomateAndroid) getActivity();
        adapter = new TriggerActionRecyclerViewAdapter(items);
        adapter.setOnItemClickListener(position -> {
            int arrayResId = automateAndroid.getOptionsForSelection(items.get(position));
            if(arrayResId == SELECTION_LAUNCH_APPLICATION){
                packageManager = getActivity().getPackageManager();
                runGetAppsThread();
                runShowAppListThread();
            }
            else{
                automateAndroid.createEnableDisableDialog(arrayResId, items.get(position), 1, items.get(position));
            }
        });
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    /*
    * In this thread a list of all installed apps will be returned and added to a list.
    * */
    private void runGetAppsThread(){
        CustomLoadingDialog customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.startLoadingDialog();
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<PackageInfo> packageList = packageManager.getInstalledPackages(0);
                for(PackageInfo info : packageList){
                    if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                        String appName = info.applicationInfo.loadLabel(packageManager).toString();
                        installedApps.add(appName);
                        Intent i = packageManager.getLaunchIntentForPackage(info.packageName);
                        labelIntentMap.put(appName, i);
                    }
                }
                customLoadingDialog.dismissDialog();
            }
        });
    }

    /*
    * This thread shows the list of apps in a dialog and user can select the app to launch
    * */
    private void runShowAppListThread(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] app = installedApps.toArray(new String[0]);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
                builder.setTitle("Select an app")
                        .setSingleChoiceItems(app, 0, null)
                        .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView listView = ((AlertDialog)dialog).getListView();
                                String selectedItem = listView.getAdapter().getItem(listView.getCheckedItemPosition()).toString();
                                AutomateAndroid activity = ((AutomateAndroid) getActivity());
                                Action action = new Action();
                                action.setLaunchAppIntent(labelIntentMap.get(selectedItem));
                                action.setActionDescription("Launch App");
                                action.setBroadcastAction(ActionTriggerConstants.LAUNCH_APPLICATION);
                                activity.getAuto().setAction(action);
                                activity.switchFragment(AutomateAndroid.REVIEW_FRAGMENT);
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
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AutomateAndroid) getActivity()).setToolBarTitle("Select Action");
        View view = inflater.inflate(R.layout.fragment_trigger_android, container, false);
        ((TextView) view.findViewById(R.id.action_trigger_description)).setText(R.string.action_explain);
        triggerRecyclerView = view.findViewById(R.id.recycler_view_tag);
        triggerRecyclerView.setHasFixedSize(true);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void getSelectionOfUser(String selection) {
        setAdapterDataSet(ACTION_ID);
        adapter.updateList(items);
        ((AutomateAndroid) getActivity()).setToolBarTitle("Select Action");
    }

    private void setAdapterDataSet(int resId){
        items = new ArrayList<>(
                Arrays.asList(getContext().getResources().getStringArray(resId))
        );
    }


}
