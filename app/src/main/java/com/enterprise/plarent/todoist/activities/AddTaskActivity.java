package com.enterprise.plarent.todoist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.adapters.PriorityDialogAdapter;
import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.adapters.ProjectAdapter;
import com.enterprise.plarent.todoist.dao.ProjectDAO;
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.model.Task;
import com.enterprise.plarent.todoist.dao.TaskDAO;

import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private EditText txtTaskName;
    private TextView projectName;
    private TextView taskPriority;
    private TextView txtColori;
    private TextView projectLabel;
    private ListView projectListView;
    private ListView priorityListView;
    private Task.TaskPriority priority_enum;
    private AlertDialog projectDialog;
    private AlertDialog priorityDialog;
    private ProjectAdapter projectAdapter;
    private PriorityDialogAdapter priorityDialogAdapter;
    private List<Project> projectList;

    private Project project;

    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;

    public static String[] priorityNames = new String[] {"Priority 1", "Priority 2", "Priority 3", "Priority 4"};
    public static int[]  priorityColors = new int[]{R.drawable.redp, R.drawable.orangep, R.drawable.lowp, R.drawable.whitep};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtColori = (TextView) findViewById(R.id.text_color);
        txtTaskName = (EditText)findViewById(R.id.txt_task_name);
        projectName = (TextView)findViewById(R.id.text_project);
        taskPriority = (TextView)findViewById(R.id.txt_priority_selected);
        projectLabel = (TextView)findViewById(R.id.txt_priority);

        Intent intent = getIntent();
        Task selectedTask = (Task) intent.getSerializableExtra("SELECTED_TASK");
        Intent intenti = getIntent();
        Project gettedProject = (Project) intenti.getSerializableExtra("PROJECT");
        Intent inter = getIntent();
        Enum receivedPriority = (Task.TaskPriority)inter.getSerializableExtra("PRIORITY");

        /*if(receivedPriority != null){
            priority_enum = (Task.TaskPriority) receivedPriority;
            projectLabel.setEnabled(false);
        }*/

        if(gettedProject == null){
        }else {
            projectName.setText(gettedProject.getProjectName());
            priority_enum = Task.TaskPriority.HIGH;
            project = gettedProject;
        }

        /*if(selectedTask == null){
        }else {
            txtTaskName.setText(selectedTask.getTaskName());
            projectName.setText(selectedTask.getProject().getProjectName());
            taskPriority.setText(selectedTask.getTaskPriority().toString());
        }*/


        txtColori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildProjectNameDialog();
            }
        });

        if(receivedPriority != null){

        }else {
            projectLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildPriorityDialog();
                }
            });
        }


        priorityDialogAdapter = new PriorityDialogAdapter(this, priorityNames, priorityColors);
        projectDAO = new ProjectDAO(this);
        taskDAO = new TaskDAO(this);
        projectList = getAllProjects();
        projectAdapter = new ProjectAdapter(this, projectList);
    }

    public static List<Project> getAllProjects(){
        return new Select()
                .from(Project.class)
                .execute();
    }

    public void buildProjectNameDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddTaskActivity.this);
        LayoutInflater inflater = AddTaskActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.project_list_dialog_layout, null);
        alertBuilder.setView(dialogView);
        projectListView = (ListView) dialogView.findViewById(R.id.project_list_dialog);
        projectListView.setAdapter(projectAdapter);

        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                project = projectList.get(position);
                projectName.setText(project.getProjectName());
                projectDialog.dismiss();
            }
        });

        projectDialog = alertBuilder.create();
        projectDialog = alertBuilder.show();
    }

    public void buildPriorityDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddTaskActivity.this);
        LayoutInflater inflater = AddTaskActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.priority_list_dialog_layout, null);
        alertBuilder.setView(dialogView);
        priorityListView = (ListView)dialogView.findViewById(R.id.priority_list_dialog);
        priorityListView.setAdapter(priorityDialogAdapter);

        priorityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        priority_enum = Task.TaskPriority.HIGH;
                        taskPriority.setText("Priority 1");
                        priorityDialog.dismiss();
                        break;
                    case 1:
                        priority_enum = Task.TaskPriority.MEDIUM;
                        taskPriority.setText("Priority 2");
                        priorityDialog.dismiss();
                        break;
                    case 2:
                        priority_enum = Task.TaskPriority.LOW;
                        taskPriority.setText("Priority 3");
                        priorityDialog.dismiss();
                        break;
                    case 3:
                        priority_enum = Task.TaskPriority.LOWEST;
                        taskPriority.setText("Priority 4");
                        priorityDialog.dismiss();
                        break;
                }
            }
        });
        priorityDialog = alertBuilder.create();
        priorityDialog = alertBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_project){
            Editable taskTitle = txtTaskName.getText();
            if(!TextUtils.isEmpty(taskTitle)){
                Task createdTask = new Task();
                createdTask.taskName = taskTitle.toString();
                createdTask.taskPriority = priority_enum;
                createdTask.mProject = project;
                createdTask.save();
                setResult(RESULT_OK);
                finish();
            }else {
                Toast.makeText(this, "The task field can't be empty.", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
