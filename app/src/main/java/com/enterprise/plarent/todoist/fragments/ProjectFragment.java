package com.enterprise.plarent.todoist.fragments;

/**
 * Created by Plarent on 8/22/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.AddProjectActivity;
import com.enterprise.plarent.todoist.activities.TaskListActivity;
import com.enterprise.plarent.todoist.adapters.ProjectAdapter;
import com.enterprise.plarent.todoist.model.Project;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProjectFragment extends Fragment {

    public static final int REQUEST_CODE_ADD_PROJECT = 40;
    public static final String EXTRA_ADDED_PROJECT = "extra_key_added_project";

    private ListView projectListView;
    private Button addProjectBtn;
    private TextView emptyProjectList;
    private ProjectAdapter projectAdapter;
    private List<Project> projectList;
    private Button addOnEmptyList;

    public ProjectFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        this.projectListView = (ListView)view.findViewById(R.id.projectListView);
        this.emptyProjectList = (TextView)view.findViewById(R.id.txt_empty_list_projects);
        FrameLayout footerL = (FrameLayout) getLayoutInflater(savedInstanceState).inflate(R.layout.footer_view, null);
        addProjectBtn = (Button) footerL.findViewById(R.id.btnGetMoreResults);
        projectListView.addFooterView(footerL);
        addOnEmptyList = (Button) view.findViewById(R.id.addProject_on_empty_list);

        projectList = getAll();
        if(projectList != null && !projectList.isEmpty()){
            addOnEmptyList.setVisibility(View.GONE);
            projectAdapter = new ProjectAdapter(getContext(), projectList);
            projectListView.setAdapter(projectAdapter);
        }else {
            emptyProjectList.setVisibility(View.VISIBLE);
            projectListView.setVisibility(View.GONE);
        }

        addOnEmptyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProjectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PROJECT);
            }
        });

        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProjectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PROJECT);
            }
        });
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project clickedProject = projectAdapter.getItem(position);
                long sendId = clickedProject.getId();
                Bundle bundle = new Bundle();

                Intent intent = new Intent(getContext(), TaskListActivity.class);
                //intent.putExtra(TaskListActivity.EXTRA_SELECTED_PROJECT_ID, clickedProject);
                //intent.putExtra(TaskListActivity.SELECTED_ID, sendId);
                bundle.putSerializable("A", clickedProject);
                bundle.putLong(TaskListActivity.SELECTED_ID, sendId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    public static List<Project> getAll() {
        return new Select()
                .from(Project.class)
                .execute();
    }
    /*public static List<Project> getAll(long id) {
        return new Select()
                .from(Project.class)
                .where("id = ?", id)
                .execute();
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_PROJECT){
            if(resultCode == RESULT_OK){
                if(data != null){
                    Project createdProject = (Project)data.getSerializableExtra(EXTRA_ADDED_PROJECT);
                    if(createdProject != null){
                        if(projectList == null){
                            projectList = new ArrayList<Project>();
                        }
                        projectList.add(createdProject);
                        if(projectAdapter == null){
                            if(projectListView.getVisibility() != View.VISIBLE){
                                addOnEmptyList.setVisibility(View.GONE);
                                projectListView.setVisibility(View.VISIBLE);
                                emptyProjectList.setVisibility(View.GONE);
                            }
                            projectAdapter = new ProjectAdapter(getContext(), projectList);
                            projectListView.setAdapter(projectAdapter);
                        }else {
                            projectAdapter.setItems(projectList);
                            projectAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
