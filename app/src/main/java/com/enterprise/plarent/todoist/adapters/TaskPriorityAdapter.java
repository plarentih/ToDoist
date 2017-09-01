package com.enterprise.plarent.todoist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.model.Task;

import java.util.List;

/**
 * Created by Plarent on 8/29/2017.
 */

public class TaskPriorityAdapter extends ArrayAdapter<Task> {
    private List<Task> tasks;

    public TaskPriorityAdapter(Context context, List<Task> tasks){
        super(context, 0, tasks);
    }

    public static class Holder{
        ImageView priority;
        TextView taskName;
        ImageView colorPro;
        TextView txtPro;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.priority_task_row, parent, false);
            holder.priority = (ImageView) convertView.findViewById(R.id.ItemTaskPriority);
            holder.taskName = (TextView) convertView.findViewById(R.id.ItemTaskName);
            holder.colorPro = (ImageView) convertView.findViewById(R.id.projectColor);
            holder.txtPro = (TextView) convertView.findViewById(R.id.projectName);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.priority.setImageResource(task.getAssociatedDrawablePriority());
        holder.taskName.setText(task.getTaskName());
        holder.colorPro.setImageResource(task.getProject().getAssociatedDrawableProject());
        holder.txtPro.setText(task.getProject().getProjectName());
        return convertView;
    }

    public void setTaskItemsat(List<Task> list){
        this.tasks = list;
    }
}
