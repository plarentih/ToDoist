package com.enterprise.plarent.todoist.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.enterprise.plarent.todoist.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Plarent on 8/23/2017.
 */
@Table(name = "Projects")
public class Project extends Model implements Serializable {

    //private long projectId;
    @Column(name = "ProjectName")
    public String projectName;
    @Column(name = "Color")
    public Color color;

    public List<Task> getTasks(){
        return getMany(Task.class, "Project");
    }

    public enum Color {RED, BLUE, GREEN, ORANGE, SILVER}

    public Project(){
        super();
    }

    public Project(String projectName, Color codeColor){
        super();
        this.projectName = projectName;
        this.color = codeColor;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectName(){
        return projectName;
    }

    public Color getColor(){
        return color;
    }

    public int getAssociatedDrawableProject(){
        return projectToDrawable(color);
    }

    public static int projectToDrawable(Color color){
        switch (color){
            case RED:
                return R.drawable.red;
            case GREEN:
                return R.drawable.green;
            case BLUE:
                return R.drawable.blue;
            case ORANGE:
                return R.drawable.orange;
            case SILVER:
                return R.drawable.silver;
        }
        return R.drawable.red;
    }
}
