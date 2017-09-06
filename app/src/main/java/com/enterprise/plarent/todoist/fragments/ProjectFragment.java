package com.enterprise.plarent.todoist.fragments;

/**
 * Created by Plarent on 8/22/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.AddProjectActivity;
import com.enterprise.plarent.todoist.activities.TaskListActivity;
import com.enterprise.plarent.todoist.adapters.ProjectAdapter;
import com.enterprise.plarent.todoist.adapters.ProjectViewAdapter;
import com.enterprise.plarent.todoist.model.Project;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class ProjectFragment extends Fragment implements ProjectViewAdapter.ClickListener{

    public static final int REQUEST_CODE_ADD_PROJECT = 40;
    public static final String EXTRA_ADDED_PROJECT = "extra_key_added_project";

    /*@BindView(R.id.projectRecyclerView) RecyclerView projectRecycleView;
    @BindView(R.id.txt_empty_list_projects) Button addProjectBtn;
    @BindView(R.id.addProject_btn) TextView emptyProjectList;*/


    private RecyclerView projectRecycleView;
    private Button addProjectBtn;
    private TextView emptyProjectList;
    private ArrayList<Project> projectList;
    private ProjectViewAdapter projectViewAdapter;

    public ProjectFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        this.projectRecycleView = (RecyclerView)view.findViewById(R.id.projectRecyclerView);
        this.emptyProjectList = (TextView)view.findViewById(R.id.txt_empty_list_projects);
        addProjectBtn = (Button) view.findViewById(R.id.addProject_btn);

        projectList = (ArrayList<Project>) getAll();
        projectViewAdapter = new ProjectViewAdapter(getContext(), projectList);
        projectViewAdapter.setClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        projectRecycleView.setLayoutManager(layoutManager);
        projectRecycleView.setItemAnimator(new DefaultItemAnimator());
        projectRecycleView.setAdapter(projectViewAdapter);


        if(projectList != null && !projectList.isEmpty()){
            emptyProjectList.setVisibility(View.GONE);
        }else {
            emptyProjectList.setVisibility(View.VISIBLE);
            projectRecycleView.setVisibility(View.GONE);
        }

        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProjectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PROJECT);
            }
        });
        return view;
    }

    @Override
    public void itemClicked(View view, int position) {
        Project clickedProject = projectList.get(position);
        long sendId = clickedProject.getId();
        Bundle bundle = new Bundle();

        Intent intent = new Intent(getContext(), TaskListActivity.class);
        bundle.putSerializable("A", clickedProject);
        bundle.putLong(TaskListActivity.SELECTED_ID, sendId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static List<Project> getAll() {
        return new Select()
                .from(Project.class)
                .execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            projectList.clear();
            projectList.addAll(getAll());
            projectViewAdapter.notifyDataSetChanged();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
