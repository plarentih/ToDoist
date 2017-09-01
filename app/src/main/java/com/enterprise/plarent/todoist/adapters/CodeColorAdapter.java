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
 * Created by Plarent on 8/24/2017.
 */

public class CodeColorAdapter extends BaseAdapter {

    String[] colorNames;
    int[] colorCodes;
    Context context;
    private static LayoutInflater inflater = null;

    public CodeColorAdapter(FragmentActivity addProjectActivity, String[]colorNamess, int[] colorCodess){
        colorNames = colorNamess;
        colorCodes = colorCodess;
        context = addProjectActivity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder{
        ImageView color;
        TextView colorName;
    }

    @Override
    public int getCount() {
        return colorNames.length;
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
        CodeColorAdapter.ViewHolder viewHolder = new CodeColorAdapter.ViewHolder();
        convertView = inflater.inflate(R.layout.colorcode_row, null);
        viewHolder.color = (ImageView) convertView.findViewById(R.id.image_color);
        viewHolder.colorName = (TextView)convertView.findViewById(R.id.color_name);
        viewHolder.color.setImageResource(colorCodes[position]);
        viewHolder.colorName.setText(colorNames[position]);
        return convertView;
    }
}
