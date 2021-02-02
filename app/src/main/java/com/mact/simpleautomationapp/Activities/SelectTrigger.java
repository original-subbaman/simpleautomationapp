package com.mact.simpleautomationapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mact.simpleautomationapp.Adapters.ListViewAdapter;
import com.mact.simpleautomationapp.Models.ListItem;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.databinding.ActivityMainBinding;
import com.mact.simpleautomationapp.databinding.ActivitySelectTriggerBinding;

import java.util.ArrayList;

public class SelectTrigger extends AppCompatActivity {

    private ActivitySelectTriggerBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySelectTriggerBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setTitle("Trigger");
        setUpListView();

    }

    private void setUpListView() {
        ArrayList<ListItem> items = new ArrayList<>();
        items.add(new ListItem(R.drawable.ic_apps, "Application"));
        items.add(new ListItem(R.drawable.ic_device, "Device"));
        ListViewAdapter adapter = new ListViewAdapter(this, items);
        mBinding.triggersListView.setAdapter(adapter);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}