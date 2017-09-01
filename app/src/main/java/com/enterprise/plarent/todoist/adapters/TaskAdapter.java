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
 * Created by Plarent on 8/28/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    private List<Task> tasks;

    public static class Holder{
        TextView taskTitle;
        ImageView taskPriority;
    }

    public TaskAdapter(Context context, List<Task> tasks){
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_row, parent, false);
            holder.taskTitle = (TextView)convertView.findViewById(R.id.ItemTaskName);
            holder.taskPriority = (ImageView)convertView.findViewById(R.id.ItemTaskPriority);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.taskTitle.setText(task.getTaskName());
        holder.taskPriority.setImageResource(task.getAssociatedDrawablePriority());
        return convertView;
    }

    public void setTaskItems(List<Task> listTask){
        this.tasks = listTask;
    }
}
