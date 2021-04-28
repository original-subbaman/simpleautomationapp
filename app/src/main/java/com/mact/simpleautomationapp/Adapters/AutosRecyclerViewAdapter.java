package com.mact.simpleautomationapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;
import java.util.List;

public class AutosRecyclerViewAdapter extends ListAdapter<AndroidAuto, AutosRecyclerViewAdapter.AndroidAutoItemViewHolder> {


    protected AutosRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }
    
    private static final DiffUtil.ItemCallback<AndroidAuto> DIFF_CALLBACK = new DiffUtil.ItemCallback<AndroidAuto>() {
        @Override
        public boolean areItemsTheSame(@NonNull AndroidAuto oldItem, @NonNull AndroidAuto newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AndroidAuto oldItem, @NonNull AndroidAuto newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getTrigger().getTriggerDescription().equals(newItem.getTrigger().getTriggerDescription()) &&
                    oldItem.getAction().getActionDescription().equals(newItem.getAction().getActionDescription());
        }
    };

    @NonNull
    @Override
    public AndroidAutoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.android_auto_item, parent, false);
        return new AndroidAutoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroidAutoItemViewHolder holder, int position) {
        AndroidAuto androidAuto = getItem(position);
        holder.mTitleText.setText(androidAuto.getTitle());
        holder.mActionText.setText(androidAuto.getAction().getActionDescription());
        holder.mTriggerText.setText(androidAuto.getTrigger().getTriggerDescription());
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

