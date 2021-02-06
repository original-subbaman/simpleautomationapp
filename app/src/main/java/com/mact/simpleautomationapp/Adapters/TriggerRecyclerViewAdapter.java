package com.mact.simpleautomationapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Models.AndroidAuto;
import com.mact.simpleautomationapp.Models.ListItem;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;

public class TriggerRecyclerViewAdapter extends RecyclerView.Adapter<TriggerRecyclerViewAdapter.TriggerItemViewHolder> {

    private ArrayList<String> mTriggerList;
    private OnItemClickListener listener;
    public TriggerRecyclerViewAdapter(ArrayList<String> list) {
        this.mTriggerList = list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public TriggerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TriggerItemViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TriggerItemViewHolder holder, int position) {
        holder.mTriggerText.setText(mTriggerList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTriggerList.size();
    }

    public static class TriggerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTriggerText;

        public TriggerItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mTriggerText = itemView.findViewById(R.id.list_item_text);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

