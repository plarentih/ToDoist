package com.enterprise.plarent.todoist.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.AddTaskActivity;
import com.enterprise.plarent.todoist.dao.TaskDAO;
import com.enterprise.plarent.todoist.model.Task;

import java.util.List;

/**
 * Created by Plarent on 8/29/2017.
 */

public class TaskPriorityAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private List<Task> taskList;


    public TaskPriorityAdapter(Activity activity, List<Task> tasks){
        this.activity = activity;
        this.taskList = tasks;
    }

    @Override
    public int getGroupCount() {
        return taskList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return taskList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public static class Holder{
        ImageView priority;
        TextView taskName;
        ImageView colorPro;
        TextView txtPro;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Task task = (Task)getGroup(groupPosition);
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.priority_task_row, null);
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

    public static class ThisHolderChild{
        ImageView editImg;
        ImageView deleteImg;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ThisHolderChild thisHolderChild;
        if(convertView == null){
            thisHolderChild = new ThisHolderChild();
            convertView = inflater.inflate(R.layout.bottom_menu, null);
            thisHolderChild.editImg = (ImageView)convertView.findViewById(R.id.edit_button);
            thisHolderChild.deleteImg = (ImageView)convertView.findViewById(R.id.delete_button);

            thisHolderChild.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = (Task)getGroup(groupPosition);
                    task.delete();
                    /*taskList.clear();
                    taskList.addAll(taskList);
                    setTaskItemsat(taskList);*/
                    Toast.makeText(v.getContext(), "Task was deleted!", Toast.LENGTH_SHORT).show();
                }
            });
            thisHolderChild.editImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = (Task)getGroup(groupPosition);
                    long id = task.getId();
                    Intent intent = new Intent(v.getContext(), AddTaskActivity.class);
                    intent.putExtra("EDIT_TASK", task);
                    intent.putExtra("TASK_ID", id);
                    activity.startActivity(intent);
                }
            });

            convertView.setTag(thisHolderChild);
        }else {
            thisHolderChild = (TaskPriorityAdapter.ThisHolderChild) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setTaskItemsat(List<Task> list){
        this.taskList = list;
    }
}
