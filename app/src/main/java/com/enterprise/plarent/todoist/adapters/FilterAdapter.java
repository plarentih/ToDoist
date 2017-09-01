package com.enterprise.plarent.todoist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.R;

/**
 * Created by Plarent on 8/29/2017.
 */

public class FilterAdapter extends BaseAdapter{

    String[] priorityNames;
    int figure;
    Context context;
    private static LayoutInflater inflater = null;

    public FilterAdapter(FragmentActivity filterActivity, String[]priorityNamess, int figuress){
        priorityNames = priorityNamess;
        figure = figuress;
        context = filterActivity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder{
        ImageView color;
        TextView colorName;
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FilterAdapter.ViewHolder viewHolder = new FilterAdapter.ViewHolder();
        convertView = inflater.inflate(R.layout.priority_row, null);
        viewHolder.color = (ImageView) convertView.findViewById(R.id.image_priority);
        viewHolder.colorName = (TextView) convertView.findViewById(R.id.priority_name);
        viewHolder.color.setImageResource(figure);
        viewHolder.colorName.setText(priorityNames[position]);
        return convertView;
    }
}
