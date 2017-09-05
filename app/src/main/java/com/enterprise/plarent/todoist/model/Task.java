package com.enterprise.plarent.todoist.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.enterprise.plarent.todoist.R;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Plarent on 8/23/2017.
 */
@Table(name = "Tasks")
public class Task extends Model implements Serializable{

    //private long taskId;
    @Column(name = "TaskName")
    public String taskName;
    @Column(name = "TaskPriority")
    public TaskPriority taskPriority;
    @Column(name = "Project")
    public Project mProject;

    public enum TaskPriority {HIGH, MEDIUM, LOW, LOWEST}

    public Task(){
        super();
    }

    public Task(String taskName, TaskPriority taskPriority, Project mProject){
        super();
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.mProject = mProject;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName(){
        return taskName;
    }

    public TaskPriority getTaskPriority(){
        return taskPriority;
    }

    public void setProject(Project project){
        this.mProject = project;
    }

    public Project getProject(){
        return mProject;
    }

    public int getAssociatedDrawablePriority(){
        return projectToDrawable(taskPriority);
    }

    public static int projectToDrawable(TaskPriority taskPriority){
        switch (taskPriority){
            case HIGH:
                return R.drawable.redp;
            case MEDIUM:
                return R.drawable.orangep;
            case LOW:
                return R.drawable.lowp;
            case LOWEST:
                return R.drawable.whitep;
        }
        return R.drawable.redp;
    }
}
