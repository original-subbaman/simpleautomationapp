package com.mact.simpleautomationapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.databinding.ActivityAutomateAndroidBinding;

public class AutomateAndroid extends AppCompatActivity {


    private ActivityAutomateAndroidBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAutomateAndroidBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

    }

    public void setToolBarTitle(String title){
        mBinding.toolbar.setTitle(title);
    }


}