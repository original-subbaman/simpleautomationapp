package com.mact.simpleautomationapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.mact.simpleautomationapp.R;

public class CustomLoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public CustomLoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null));
        builder.setCancelable(true);

        this.alertDialog = builder.create();
        this.alertDialog.show();

    }

    public void dismissDialog(){
        this.alertDialog.dismiss();
    }
}
