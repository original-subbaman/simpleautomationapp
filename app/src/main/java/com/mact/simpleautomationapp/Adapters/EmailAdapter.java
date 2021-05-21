package com.mact.simpleautomationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.EmailCred;

import java.util.ArrayList;

public class EmailAdapter extends ArrayAdapter<EmailCred> {

    public EmailAdapter(@NonNull Context context, ArrayList<EmailCred> emails) {
        super(context, 0, emails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return  initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_email_spinner, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.email_from);
        EmailCred email = getItem(position);

        if(email != null){
            textView.setText(email.getEmailID());
        }

        return convertView;
    }
}
