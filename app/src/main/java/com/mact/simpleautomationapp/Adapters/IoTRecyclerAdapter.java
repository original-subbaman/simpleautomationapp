package com.mact.simpleautomationapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mact.simpleautomationapp.Activities.SelectIoT;
import com.mact.simpleautomationapp.Models.IoTDevice;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;
import java.util.List;

public class IoTRecyclerAdapter extends RecyclerView.Adapter<IoTRecyclerAdapter.IoTViewHolder> {

    private List<IoTDevice> iotDevices;
    public static final String TAG = "IOTRECYCLERVIEW";
    private OnCheckedChangeListener listener;
    private Context context;

    public interface OnCheckedChangeListener {
        void onCheckedChange(CompoundButton button, boolean isChecked);
    }

    public void setOnItemClickListener(OnCheckedChangeListener listener){
        this.listener = listener;
    }

    public IoTRecyclerAdapter(List<IoTDevice> iotDevices, Context context){
        this.iotDevices = new ArrayList<>();
        this.context = context;
    }

    public List<IoTDevice> getIotDevices(){
        return this.iotDevices;
    }

    @NonNull
    @Override
    public IoTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iot_item, parent, false);
        return new IoTViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull IoTRecyclerAdapter.IoTViewHolder holder, int position) {
        holder.iotDeviceName.setText(iotDevices.get(position).getDeviceName());
        int checked = iotDevices.get(position).getValue();
        if(checked == 0){
            holder.aSwitch.setChecked(false);
        }else{
            holder.aSwitch.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return iotDevices.size();
    }

    private void modifyDataset(int pos, boolean isChecked){
        int value = 0;
        if(isChecked){
            value = 1;
        }
        ((SelectIoT) context).modifyValue(value, pos);

    }

    public static class IoTViewHolder extends RecyclerView.ViewHolder{
        private TextView iotDeviceName;
        private Switch aSwitch;
        public IoTViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            iotDeviceName = itemView.findViewById(R.id.iot_device_name);
            aSwitch = itemView.findViewById(R.id.iot_switch);

            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        int value = isChecked ? 1 : 0;
                        ((SelectIoT) context).modifyValue(value, pos);
                    }
                }
            });
        }
    }
}
