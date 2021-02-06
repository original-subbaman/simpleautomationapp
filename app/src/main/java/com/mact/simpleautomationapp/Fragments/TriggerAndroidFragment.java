package com.mact.simpleautomationapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Adapters.TriggerRecyclerViewAdapter;
import com.mact.simpleautomationapp.Models.ListItem;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Utils.CustomDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mact.simpleautomationapp.Utils.CustomDialog.TAG;

public class TriggerAndroidFragment extends Fragment {

    private ArrayList<String> items;
    private RecyclerView triggerRecyclerView;
    private TriggerRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public TriggerAndroidFragment(){
        super(R.layout.fragment_trigger_android);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<>(
                Arrays.asList(getContext().getResources().getStringArray(R.array.triggers))
        );
        adapter = new TriggerRecyclerViewAdapter(items);
        adapter.setOnItemClickListener(new TriggerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int arrayResId = getOptions(items.get(position));
                CustomDialog dialog = new CustomDialog(arrayResId, "Select", getContext());
                dialog.show(getActivity().getSupportFragmentManager(), "CustomDialog");

            }
        });
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AutomateAndroid) getActivity()).setToolBarTitle("Select Trigger");
        View view = inflater.inflate(R.layout.fragment_trigger_android, container, false);
        triggerRecyclerView = view.findViewById(R.id.recycler_view_tag);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        triggerRecyclerView.setHasFixedSize(true);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public int getOptions(String triggerSelected){
        int resId = 0;
        switch(triggerSelected){
            case "Airplane Mode":
                resId = R.array.airplane_mode_options;
                break;
            case "Battery Level":
                resId = R.array.battery_level;
                break;
            case "WiFi_state_changed":
                resId = R.array.WiFi_state_change;
                break;
        }
        return resId;
    }

}
