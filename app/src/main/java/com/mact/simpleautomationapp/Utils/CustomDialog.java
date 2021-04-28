package com.mact.simpleautomationapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.R;

public class CustomDialog extends DialogFragment {
    private int resId;
    private String title;
    private AlertDialog.Builder builder;
    private Context context;
    public static final String TAG = "Custom Dialog";
    private CustomDialogContract contract;
    public CustomDialog(int resId, String title, Context context) {
        this.resId = resId;
        this.title = title;
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setTitle(R.string.select)
                .setSingleChoiceItems(resId, 0, null)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView listView = ((AlertDialog)dialog).getListView();
                        String checkedItem = listView.getAdapter().getItem(listView.getCheckedItemPosition()).toString();
                        ((CustomDialogContract) getTargetFragment()).getSelectionOfUser(checkedItem);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
