package com.enterprise.plarent.todoist.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.dao.TaskDAO;
import com.enterprise.plarent.todoist.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Plarent on 8/31/2017.
 */

public class ExpandableTaskListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private List<Task> taskList;
    private Map<String, List<Task>> underMenu;
    private TaskDAO taskDAO;

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
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Task task = (Task)getGroup(groupPosition);
        ThisHolder thisHolder;
        if(convertView == null){
            thisHolder = new ThisHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_row, null);
            thisHolder.taskPriority = (ImageView)convertView.findViewById(R.id.ItemTaskPriority);
            thisHolder.taskTitle = (TextView)convertView.findViewById(R.id.ItemTaskName);
            convertView.setTag(thisHolder);
        }else {
            thisHolder = (ThisHolder)convertView.getTag();
        }
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
        taskDAO = new TaskDAO(activity);
        String str = (String) getChild(groupPosition,childPosition);

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
}
