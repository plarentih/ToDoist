package com.enterprise.plarent.todoist.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.model.Task;

/**
 * Created by Plarent on 8/23/2017.
 */

public class ProjectDAO {

    public static final String TAG = "ProjectDAO";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context context;
    private String[] mAllColumns = {DBHelper.COLUMN_PROJECT_ID, DBHelper.COLUMN_PROJECT_NAME,
            DBHelper.COLUMN_PROJECT_COLORCODE};

    public ProjectDAO(Context context) {
        this.context = context;
        mDbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close(){
        mDbHelper.close();
    }

    public Project createProject(String name, Project.Color codeColor){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PROJECT_NAME, name);
        values.put(DBHelper.COLUMN_PROJECT_COLORCODE, codeColor.name());
        long insertId = mDatabase.insert(DBHelper.TABLE_PROJECTS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PROJECTS, mAllColumns,
                DBHelper.COLUMN_PROJECT_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Project newProject = cursorToProject(cursor);
        cursor.close();
        return newProject;
    }

    /*public void deleteProject(Project project){
        long id = project.getProjectId();
        TaskDAO dao = new TaskDAO(context);
        List<Task> listTask = dao.getTasksOfProject(id);
        if(listTask != null && !listTask.isEmpty()){
            for(Task t : listTask){
                dao.deleteTask(t);
            }
        }
        mDatabase.delete(DBHelper.TABLE_PROJECTS, DBHelper.COLUMN_PROJECT_ID + " = " + id, null);
    }*/

    public List<Project> getAllProjects(){
        List<Project> listProjects = new ArrayList<Project>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PROJECTS, mAllColumns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Project project = cursorToProject(cursor);
                listProjects.add(project);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listProjects;
    }

    public Project getProjectById(long id){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PROJECTS, mAllColumns,
                DBHelper.COLUMN_PROJECT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null ){
            cursor.moveToFirst();
        }
        Project project = cursorToProject(cursor);
        return  project;
    }

    protected Project cursorToProject(Cursor cursor){
        Project project = new Project();
        /*project.setProjectId(cursor.getLong(0));
        project.setProjectName(cursor.getString(1));
        Project.Color.valueOf(cursor.getString(2));*/
        return project;
    }
}
