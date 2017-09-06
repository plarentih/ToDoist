package com.enterprise.plarent.todoist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.TaskListActivity;
import com.enterprise.plarent.todoist.model.Project;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Plarent on 9/5/2017.
 */

public class ProjectViewAdapter extends RecyclerView.Adapter<ProjectViewAdapter.MyViewHolder> {

    private List<Project> projectList;
    private Context context;
    private ClickListener clickListener;

    public ProjectViewAdapter(Context context, List<Project> projects){
        this.context = context;
        projectList = projects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.title.setText(project.getProjectName());
        holder.colorCode.setImageResource(project.getAssociatedDrawableProject());
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.listItemProjectTitle) TextView title;
        @BindView(R.id.listItemProjectImg) ImageView colorCode;

        public MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public void swapItems(List<Project> newItems){
        projectList.clear();
        projectList.addAll(newItems);
        notifyDataSetChanged();
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}
