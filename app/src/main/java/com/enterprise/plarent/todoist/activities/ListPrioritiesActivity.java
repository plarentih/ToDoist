package com.enterprise.plarent.todoist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.adapters.TaskAdapter;
import com.enterprise.plarent.todoist.model.Task;
import com.enterprise.plarent.todoist.dao.TaskDAO;
import com.enterprise.plarent.todoist.adapters.TaskPriorityAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListPrioritiesActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private TaskDAO taskDAO;
    private List<Task> taskList;
    private TaskPriorityAdapter taskAdapter;
    private Task.TaskPriority priority;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_priorities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ExpandableListView)findViewById(R.id.taskListView);
        taskDAO = new TaskDAO(this);
        Intent intent = getIntent();
        String pri = (String) intent.getSerializableExtra("PRIORITY");

        switch (pri){
            case "Priority 1":
                priority = Task.TaskPriority.HIGH;
                break;
            case "Priority 2":
                priority = Task.TaskPriority.MEDIUM;
                break;
            case "Priority 3":
                priority = Task.TaskPriority.LOW;
                break;
            case "Priority 4":
                priority = Task.TaskPriority.LOWEST;
                break;
        }
        taskList = getTasksOfPriority(priority);
        taskAdapter = new TaskPriorityAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddTaskActivity.class);
                intent.putExtra("PRIORITY", priority);
                startActivityForResult(intent, TaskListActivity.REQUEST_CODE_ADD_TASK);
            }
        });

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup){
                    listView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });
    }

    public static List<Task> getTasksOfPriority(Task.TaskPriority taskPriority){
        return new Select()
                .from(Task.class)
                .where("TaskPriority = ?", taskPriority)
                .execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TaskListActivity.REQUEST_CODE_ADD_TASK){
            if(resultCode == RESULT_OK){
                if(taskList == null || !taskList.isEmpty()){
                    taskList = new ArrayList<Task>();
                }
                if(taskDAO == null){
                    taskDAO = new TaskDAO(this);
                }
                taskList = getTasksOfPriority(priority);

                if(taskAdapter == null){
                    taskAdapter = new TaskPriorityAdapter(this, taskList);
                    listView.setAdapter(taskAdapter);
                    if(listView.getVisibility() != View.VISIBLE){
                        listView.setVisibility(View.VISIBLE);
                    }
                }else {
                    taskAdapter.setTaskItemsat(taskList);
                    taskAdapter.notifyDataSetChanged();
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
