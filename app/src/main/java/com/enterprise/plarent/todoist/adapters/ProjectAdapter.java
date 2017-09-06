package com.enterprise.plarent.todoist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plarent on 8/24/2017.
 */

public class ProjectAdapter extends ArrayAdapter<Project> {

    private ArrayList<Project> items = new ArrayList<>();

    public static class ViewHolder{
        TextView title;
        ImageView colorCode;
    }

    public ProjectAdapter(Context context, List<Project> projectsList){
        super(context, 0, projectsList);
        items.addAll(projectsList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Project project = items.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.project_row, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.listItemProjectTitle);
            viewHolder.colorCode = (ImageView) convertView.findViewById(R.id.listItemProjectImg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(project.getProjectName());
        viewHolder.colorCode.setImageResource(project.getAssociatedDrawableProject());
        return convertView;
    }

    public void setItems(ArrayList<Project> list){
        this.items = list;
    }

    public void swapItems(List<Project> newItems){
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
