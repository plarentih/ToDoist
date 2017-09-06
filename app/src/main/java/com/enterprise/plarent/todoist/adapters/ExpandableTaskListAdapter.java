package com.enterprise.plarent.todoist.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.AddTaskActivity;
import com.enterprise.plarent.todoist.activities.TaskListActivity;
import com.enterprise.plarent.todoist.dao.TaskDAO;
import com.enterprise.plarent.todoist.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Plarent on 8/31/2017.
 */

public class ExpandableTaskListAdapter extends BaseExpandableListAdapter  {

    private Activity activity;
    private List<Task> taskList;

    public ExpandableTaskListAdapter(Activity activity, List<Task> taskList) {
        this.activity = activity;
        this.taskList = taskList;
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

    public static class ThisHolder{
        TextView taskTitle;
        ImageView taskPriority;
        ImageView editbtn;
        ImageView deletebtn;
        RelativeLayout relativeLayout;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Task task = (Task)getGroup(groupPosition);
        ThisHolder thisHolder;
        if(convertView == null){
            thisHolder = new ThisHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_row, null);
            thisHolder.taskPriority = (ImageView)convertView.findViewById(R.id.ItemTaskPriority);
            thisHolder.taskTitle = (TextView)convertView.findViewById(R.id.ItemTaskName);

            thisHolder.relativeLayout = (RelativeLayout)convertView.findViewById(R.id.bottom_menu_btn);
            thisHolder.editbtn = (ImageView)convertView.findViewById(R.id.edit_button);
            thisHolder.deletebtn  = (ImageView)convertView.findViewById(R.id.delete_button);

            convertView.setTag(thisHolder);
        }else {
            thisHolder = (ThisHolder)convertView.getTag();
        }
        thisHolder.relativeLayout.setVisibility(View.GONE);
        thisHolder.editbtn.setOnClickListener(new View.OnClickListener() {
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
        thisHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = (Task)getGroup(groupPosition);
                task.delete();
                taskList.clear();
                taskList.addAll(taskList);
                setTaskItems(taskList);
                Toast.makeText(v.getContext(), "Task was deleted!", Toast.LENGTH_SHORT).show();
            }
        });
        thisHolder.taskPriority.setImageResource(task.getAssociatedDrawablePriority());
        thisHolder.taskTitle.setText(task.getTaskName());
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
                    taskList.remove(groupPosition);
                    notifyDataSetChanged();
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
            thisHolderChild = (ThisHolderChild) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setTaskItems(List<Task> listTask){
        this.taskList = listTask;
    }

    public void swapItems(List<Task> newItems){
        taskList.clear();
        taskList.addAll(newItems);
        notifyDataSetChanged();
    }
}
