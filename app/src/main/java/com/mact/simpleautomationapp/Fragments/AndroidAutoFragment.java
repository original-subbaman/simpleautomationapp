package com.mact.simpleautomationapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Adapters.AutosRecyclerViewAdapter;
import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Entity.Trigger;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class AndroidAutoFragment extends Fragment {
    private ArrayList<AndroidAuto> androidAutoFragmentArrayList;
    private RecyclerView mRecyclerView;
    public AutosRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AutomatedTaskViewModel viewModel;

    public AndroidAutoFragment() {
        super(R.layout.android_auto_item);
        androidAutoFragmentArrayList = new ArrayList<>();
        AndroidAuto auto = new AndroidAuto("Title 1");
        auto.setTrigger(new Trigger("Desc 1"));
        auto.setAction(new Action("Desc 1"));

        AndroidAuto auto1 = new AndroidAuto("Title 2");
        auto1.setTrigger(new Trigger("Trigger Desc 2"));
        auto1.setAction(new Action("Action Desc 2"));

        AndroidAuto auto2 = new AndroidAuto("Title 3");
        auto2.setTrigger(new Trigger("Trigger Desc 3"));
        auto2.setAction(new Action("Action Desc 3"));

        androidAutoFragmentArrayList.add(auto);
        androidAutoFragmentArrayList.add(auto1);
        androidAutoFragmentArrayList.add(auto2);

        viewModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AutomatedTaskViewModel.class);
        setLiveDataObserver();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new AutosRecyclerViewAdapter(androidAutoFragmentArrayList);
        mLayoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android_auto, container, false);
        mRecyclerView = view.findViewById(R.id.android_auto_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setLiveDataObserver(){
        viewModel.getAllAndroidTasks().observe(this, new Observer<List<AndroidAuto>>() {
            @Override
            public void onChanged(List<AndroidAuto> androidAutos) {
                mAdapter.submitList(androidAutos);
            }
        });
    }
}
