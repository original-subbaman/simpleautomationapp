package com.mact.simpleautomationapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import static com.mact.simpleautomationapp.Utils.CustomDialog.TAG;

public class CreateTaskFragment extends Fragment implements CustomDialogContract {

    private ArrayList<String> items;
    private RecyclerView triggerRecyclerView;
    private TriggerActionRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private static final int TRIGGER_ID = R.array.triggers;
    private static final int ACTION_ID = R.array.actions;
    public CreateTaskFragment(){
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
        adapter = new TriggerActionRecyclerViewAdapter(items);
        adapter.setOnItemClickListener(new TriggerActionRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int arrayResId = getOptionsForSelection(items.get(position));
                CustomDialog dialog = new CustomDialog(arrayResId, "Select", getContext());
                dialog.setTargetFragment(CreateTaskFragment.this, 0);
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
        triggerRecyclerView.setHasFixedSize(true);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public int getOptionsForSelection(String selection){
       if(selection.equals("Launch Application")){
           return -1;
       }
        return R.array.enable_disable;
    }


    @Override
    public void getSelectionOfUser(String selection) {
        setAdapterDataSet(ACTION_ID);
        adapter.updateList(items);
        Log.d(TAG, "getSelectionOfUser: " + items.get(0));
    }

    public void setAdapterDataSet(int resId){
        items = new ArrayList<>(
                Arrays.asList(getContext().getResources().getStringArray(resId))
        );
    }
}
