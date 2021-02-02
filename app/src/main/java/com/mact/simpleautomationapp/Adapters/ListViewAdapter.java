package com.mact.simpleautomationapp.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mact.simpleautomationapp.Models.ListItem;
import com.mact.simpleautomationapp.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private final Activity context;
    private ArrayList<ListItem> items;

    public ListViewAdapter(Activity context, ArrayList<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.list_item, null, true);

        }
        TextView triggerTitle = convertView.findViewById(R.id.list_item_text);
        ImageView imageView = convertView.findViewById(R.id.list_item_image);
        triggerTitle.setText(items.get(position).getText());
        imageView.setImageResource(items.get(position).getImageResId());
        return convertView;
    }
}
