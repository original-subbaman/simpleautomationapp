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

import com.mact.simpleautomationapp.Activities.MainActivity;
import com.mact.simpleautomationapp.Adapters.AutosRecyclerViewAdapter;
import com.mact.simpleautomationapp.Models.AndroidAuto;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;

public class AndroidAutoFragment extends Fragment {


    private ArrayList<AndroidAuto> androidAutoFragmentArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public AndroidAutoFragment() {
        super(R.layout.android_auto_item);
        androidAutoFragmentArrayList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidAutoFragmentArrayList.add(new AndroidAuto("Title1", "Description1", "Action1"));
        androidAutoFragmentArrayList.add(new AndroidAuto("Title2", "Description2", "Action2"));
        androidAutoFragmentArrayList.add(new AndroidAuto("Title3", "Description3", "Action3"));
        mAdapter = new AutosRecyclerViewAdapter(androidAutoFragmentArrayList);
        mLayoutManager = new LinearLayoutManager(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Android Autos");
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
}
