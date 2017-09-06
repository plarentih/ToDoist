package com.enterprise.plarent.todoist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.adapters.ExpandableTaskListAdapter;
import com.enterprise.plarent.todoist.adapters.TaskAdapter;
import com.enterprise.plarent.todoist.dao.ProjectDAO;
import com.enterprise.plarent.todoist.dao.TaskDAO;
import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TASK = 40;
    public static final String SELECTED_ID = "extra_selected_id";

    private ExpandableTaskListAdapter expandableTaskListAdapter;

    private ExpandableListView taskListView;
    private TextView txtEmptyTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TaskDAO taskDAO;
    private Project project;
    private Task task;
    private long receivedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtEmptyTaskList = (TextView)findViewById(R.id.txt_empty_list_tasks);
        taskListView = (ExpandableListView)findViewById(R.id.tasksListView);

        Intent intent = getIntent();

        if(intent != null){
            project = (Project)getIntent().getSerializableExtra("A");
            receivedId  = intent.getLongExtra(SELECTED_ID, -1);
        }

        if(project != null){
            taskList = getTasksOfProject(receivedId);
            sortTasksOnPriority();
            if(taskList != null && !taskList.isEmpty()){
                expandableTaskListAdapter = new ExpandableTaskListAdapter(this, taskList);
                taskListView.setAdapter(expandableTaskListAdapter);
            }else {
                txtEmptyTaskList.setVisibility(View.VISIBLE);
                taskListView.setVisibility(View.GONE);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenti = new Intent(getBaseContext(), AddTaskActivity.class);
                intenti.putExtra("PROJECT", project);
                intenti.putExtra("ID", receivedId);
                startActivityForResult(intenti, REQUEST_CODE_ADD_TASK);
            }
        });

        taskListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup){
                    taskListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });

    }

    public static List<Task> getTasksOfProject(long id) {
        return new Select()
                .from(Task.class)
                .where("Project = ?", id)
                .execute();
    }

    private List<Task> sortTasksOnPriority(){
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getTaskPriority() == o2.getTaskPriority()){
                    return o1.getTaskName().compareTo(o2.getTaskName());
                }else {
                    return o1.getTaskPriority().compareTo(o2.getTaskPriority());
                }
            }
        });
        return taskList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_TASK){
            if(resultCode == RESULT_OK){
                if(taskList == null || !taskList.isEmpty()){
                    taskList = new ArrayList<Task>();
                }
                taskList = getTasksOfProject(receivedId);

                sortTasksOnPriority();

                if(expandableTaskListAdapter == null){
                    expandableTaskListAdapter = new ExpandableTaskListAdapter(this, taskList);
                    taskListView.setAdapter(expandableTaskListAdapter);
                    if(taskListView.getVisibility() != View.VISIBLE){
                        taskListView.setVisibility(View.VISIBLE);
                        txtEmptyTaskList.setVisibility(View.GONE);
                    }
                }else {
                    expandableTaskListAdapter.setTaskItems(taskList);
                    expandableTaskListAdapter.notifyDataSetChanged();
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
