package com.enterprise.plarent.todoist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Plarent on 8/23/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_PROJECT_ID = "_id";
    public static final String COLUMN_PROJECT_NAME = "project_name";
    public static final String COLUMN_PROJECT_COLORCODE = "color_code";

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TAKS_ID = COLUMN_PROJECT_ID;
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_PROJECT_ID = "project_id";

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_PROJECTS + "("
            + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PROJECT_NAME + " TEXT NOT NULL, "
            + COLUMN_PROJECT_COLORCODE + " TEXT NOT NULL "
            +");";

    private static final String SQL_CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
            + COLUMN_TAKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TASK_NAME + " TEXT NOT NULL, "
            + COLUMN_TASK_PRIORITY + " TEXT NOT NULL, "
            + COLUMN_TASK_PROJECT_ID + " INTEGER NOT NULL "
            +");";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PROJECTS);
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        onCreate(db);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
