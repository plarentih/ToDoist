package com.enterprise.plarent.todoist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plarent on 8/23/2017.
 */

public class TaskDAO {

    public static final String TAG = "TaskDAO";

    private Context context;

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColums = {DBHelper.COLUMN_TAKS_ID, DBHelper.COLUMN_TASK_NAME,
                DBHelper.COLUMN_TASK_PRIORITY, DBHelper.COLUMN_TASK_PROJECT_ID};

    public TaskDAO(Context context){
        mDbHelper = new DBHelper(context);
        this.context = context;
        try {
            open();
        }catch (SQLException e){
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

    public Task createTask(String name, Task.TaskPriority priority, long projectId){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TASK_NAME, name);
        values.put(DBHelper.COLUMN_TASK_PRIORITY, priority.name());
        values.put(DBHelper.COLUMN_TASK_PROJECT_ID, projectId);
        long insertId = mDatabase.insert(DBHelper.TABLE_TASKS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASKS, mAllColums,
                DBHelper.COLUMN_TAKS_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public long updateTask(long taskId, String name, Task.TaskPriority priority, long projectId){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TASK_NAME, name);
        values.put(DBHelper.COLUMN_TASK_PRIORITY, priority.name());
        values.put(DBHelper.COLUMN_TASK_PROJECT_ID, projectId);
        return mDatabase.update(DBHelper.TABLE_TASKS, values, DBHelper.COLUMN_TAKS_ID + " = " + taskId, null);
    }

    /*public void deleteTask(Task task){
        long id = task.getTaskId();
        mDatabase.delete(DBHelper.TABLE_TASKS, DBHelper.COLUMN_TAKS_ID + " = " + id, null);
    }*/

    public List<Task> getAllTasks(){
        List<Task> listTask = new ArrayList<Task>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASKS, mAllColums, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Task task = cursorToTask(cursor);
            listTask.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return listTask;
    }

    public List<Task> getTasksOfProject(long projectId){
        List<Task> listTasks = new ArrayList<Task>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASKS, mAllColums,
                DBHelper.COLUMN_TASK_PROJECT_ID + " = ?", new String[] {String.valueOf(projectId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Task task = cursorToTask(cursor);
            listTasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return listTasks;
    }

    public List<Task> getTasksOnPriority(Task.TaskPriority priority){
        List<Task> listTask = new ArrayList<Task>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASKS, mAllColums,
                DBHelper.COLUMN_TASK_PRIORITY + " = ?", new String[]{String.valueOf(priority)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Task task = cursorToTask(cursor);
            listTask.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return listTask;
    }

    private Task cursorToTask(Cursor cursor){
        Task task = new Task();
        long projectId = cursor.getLong(3);
        ProjectDAO dao = new ProjectDAO(context);
        Project project = dao.getProjectById(projectId);
        if(project != null){
            task.setProject(project);
        }
        return task;
    }
}
