package com.mact.simpleautomationapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Adapters.TriggerActionRecyclerViewAdapter;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Utils.CustomDialog;
import com.mact.simpleautomationapp.Utils.CustomDialogContract;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectTriggerFragment extends Fragment {

    private ArrayList<String> items;
    private RecyclerView triggerRecyclerView;
    private TriggerActionRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private AutomateAndroid automateAndroid;
    private static final int TRIGGER_ID = R.array.triggers;

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
            String selectedItem = items.get(position);
            if(selectedItem.equals("Time")){
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(automateAndroid.getSupportFragmentManager(), "timePicker");
            }else{
                int arrayResId = automateAndroid.getOptionsForSelection(items.get(position));
                automateAndroid.createEnableDisableDialog(arrayResId, items.get(position), 0, items.get(position));
            }


        });
        linearLayoutManager = new LinearLayoutManager(getContext());
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
}
