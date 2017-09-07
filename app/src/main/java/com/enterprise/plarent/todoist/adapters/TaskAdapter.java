package com.enterprise.plarent.todoist.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        ImageView editBtn;
        ImageView deleteBtn;
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
            holder.editBtn = (ImageView)convertView.findViewById(R.id.edit_button);
            holder.deleteBtn= (ImageView)convertView.findViewById(R.id.delete_button);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.editBtn.setVisibility(View.GONE);
        holder.deleteBtn.setVisibility(View.GONE);

        holder.taskTitle.setText(task.getTaskName());
        holder.taskPriority.setImageResource(task.getAssociatedDrawablePriority());
        return convertView;
    }

    public void setTaskItems(List<Task> listTask){
        this.tasks = listTask;
    }
}
