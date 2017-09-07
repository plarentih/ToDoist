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
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.adapters.PriorityDialogAdapter;
import com.enterprise.plarent.todoist.adapters.ProjectAdapter;
import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.model.Task;

import java.util.ArrayList;
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
    private ArrayList<Project> projectList;
    private long receivedId;
    private long taskId;
    private long taskPriorityId;

    private Project project;
    private Task selectedTask;
    private Task selectedPriorityTask;

    private Project gettedProject;


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
        selectedTask = (Task) intent.getSerializableExtra("EDIT_TASK");
        taskId = intent.getLongExtra("TASK_ID", -1);

        Intent intentEditPriority = getIntent();
        selectedPriorityTask = (Task)intentEditPriority.getSerializableExtra("EDIT_TASK_WITH_PRIORITY");
        taskPriorityId = intentEditPriority.getLongExtra("TASK_ID_WITH_PRIORITY", -1);

        Intent intenti = getIntent();
        gettedProject = (Project) intenti.getSerializableExtra("PROJECT");
        receivedId = intent.getLongExtra("ID", -1);

        //me shtu task prej priority list, te vec ni prioriteti
        Intent inter = getIntent();
        Enum receivedPriority = (Task.TaskPriority)inter.getSerializableExtra("PRIORITY");

        if(receivedPriority != null){
            priority_enum = (Task.TaskPriority) receivedPriority;
            projectName.setText(getAllProjects().get(0).getProjectName());
            project = getAllProjects().get(0);
            projectLabel.setEnabled(false);
        }

        if(gettedProject == null){
        }else {
            projectName.setText(gettedProject.getProjectName());
            priority_enum = Task.TaskPriority.HIGH;
            project = getProjectById(receivedId);
        }

        if(selectedTask == null){
        }else {
            Task task = Task.load(Task.class, taskId);
            project = task.getProject();
            txtTaskName.setText(selectedTask.getTaskName());
            projectName.setText(selectedTask.getProject().getProjectName());
            String prio = selectedTask.getTaskPriority().name();
            switch(prio){
                case "HIGH":
                    taskPriority.setText("Priority 1");
                    priority_enum = Task.TaskPriority.HIGH;
                    break;
                case "MEDIUM":
                    taskPriority.setText("Priority 2");
                    priority_enum = Task.TaskPriority.MEDIUM;
                    break;
                case "LOW":
                    taskPriority.setText("Priority 3");
                    priority_enum = Task.TaskPriority.LOW;
                    break;
                case "LOWEST":
                    taskPriority.setText("Priority 4");
                    priority_enum = Task.TaskPriority.LOWEST;
                    break;
            }
        }

        if(selectedPriorityTask == null){
        }else {
            Task task = Task.load(Task.class, taskPriorityId);
            project = task.getProject();
            txtTaskName.setText(selectedPriorityTask.getTaskName());
            projectName.setText(selectedPriorityTask.getProject().getProjectName());
            projectLabel.setClickable(false);
            projectLabel.setEnabled(false);
            String pro = selectedPriorityTask.getTaskPriority().name();
            switch (pro){
                case "HIGH":
                    taskPriority.setText("Priority 1");
                    priority_enum = Task.TaskPriority.HIGH;
                    break;
                case "MEDIUM":
                    taskPriority.setText("Priority 2");
                    priority_enum = Task.TaskPriority.MEDIUM;
                    break;
                case "LOW":
                    taskPriority.setText("Priority 3");
                    priority_enum = Task.TaskPriority.LOW;
                    break;
                case "LOWEST":
                    taskPriority.setText("Priority 4");
                    priority_enum = Task.TaskPriority.LOWEST;
                    break;
            }
        }


        txtColori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildProjectNameDialog();
            }
        });

        if(receivedPriority != null){
            String as = receivedPriority.name();
            switch (as){
                case "HIGH":
                    taskPriority.setText("Priority 1");
                    break;
                case "MEDIUM":
                    taskPriority.setText("Priority 2");
                    break;
                case "LOW":
                    taskPriority.setText("Priority 3");
                    break;
                case "LOWEST":
                    taskPriority.setText("Priority 4");
                    break;
            }
        }else {
            projectLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildPriorityDialog();
                }
            });
        }


        priorityDialogAdapter = new PriorityDialogAdapter(this, priorityNames, priorityColors);
        projectList = (ArrayList<Project>) getAllProjects();
        projectAdapter = new ProjectAdapter(this, projectList);
    }

    public static Project getProjectById(long id){
        return new Select()
                .from(Project.class)
                .where("id = ?", id)
                .executeSingle();
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
            Project pro = getProjectById(receivedId);
            Editable taskTitle = txtTaskName.getText();
            if(selectedTask != null){
                Task task = Task.load(Task.class, taskId);
                task.taskName = taskTitle.toString();
                task.taskPriority = priority_enum;
                task.mProject = project;
                task.save();
                setResult(RESULT_OK);
                finish();
                return true;
            }
            if(selectedPriorityTask != null){
                Task task = Task.load(Task.class, taskPriorityId);
                task.taskName = taskTitle.toString();
                task.taskPriority = priority_enum;
                task.mProject = project;
                task.save();
                setResult(RESULT_OK);
                finish();
                return true;
            }
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
