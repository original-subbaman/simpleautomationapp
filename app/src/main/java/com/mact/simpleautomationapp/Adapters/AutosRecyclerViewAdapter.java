package com.mact.simpleautomationapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Models.AndroidAuto;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;

public class AutosRecyclerViewAdapter extends RecyclerView.Adapter<AutosRecyclerViewAdapter.AndroidAutoItemViewHolder> {

    private ArrayList<AndroidAuto> mAutoList;
    public AutosRecyclerViewAdapter(ArrayList<AndroidAuto> list) {
        this.mAutoList = list;
    }

    @NonNull
    @Override
    public AndroidAutoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.android_auto_item, parent, false);
        return new AndroidAutoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroidAutoItemViewHolder holder, int position) {
        holder.mTitleText.setText(mAutoList.get(position).getTitle());
        holder.mActionText.setText(mAutoList.get(position).getActionDescription());
        holder.mTriggerText.setText(mAutoList.get(position).getTriggerDescription());
    }

    @Override
    public int getItemCount() {
        return mAutoList.size();
    }

    public static class AndroidAutoItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitleText;
        private final TextView mTriggerText;
        private final TextView mActionText;

        public AndroidAutoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.android_auto_title);
            mTriggerText = itemView.findViewById(R.id.android_auto_trigger_description);
            mActionText = itemView.findViewById(R.id.android_auto_action_description);
        }
    }
}

