package com.enterprise.plarent.todoist.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.R;

/**
 * Created by Plarent on 8/28/2017.
 */

public class PriorityDialogAdapter extends BaseAdapter {

    String[] priorityNames;
    int [] priorityCodes;
    Context context;
    private static LayoutInflater inflater = null;

    public PriorityDialogAdapter(Activity activity, String[]priorityNames, int[] priorityCodes){
        this.priorityNames = priorityNames;
        this.priorityCodes = priorityCodes;
        context = activity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holderi{
        ImageView color;
        TextView priorityColor;
    }

    @Override
    public int getCount() {
        return priorityNames.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holderi holderi = new Holderi();
        convertView = inflater.inflate(R.layout.priority_row, null);
        holderi.color = (ImageView)convertView.findViewById(R.id.image_priority);
        holderi.priorityColor = (TextView)convertView.findViewById(R.id.priority_name);
        holderi.color.setImageResource(priorityCodes[position]);
        holderi.priorityColor.setText(priorityNames[position]);
        return convertView;
    }
}
