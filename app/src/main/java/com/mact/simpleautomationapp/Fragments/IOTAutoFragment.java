package com.mact.simpleautomationapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Adapters.AutosRecyclerViewAdapter;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;

public class IOTAutoFragment extends Fragment {


    private ArrayList<AndroidAuto> androidAutoFragmentArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public IOTAutoFragment() {
        super(R.layout.android_auto_item);
        androidAutoFragmentArrayList = new ArrayList<>();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new AutosRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iot_auto, container, false);
        mRecyclerView = view.findViewById(R.id.android_iot_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
