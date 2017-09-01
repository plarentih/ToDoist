package com.enterprise.plarent.todoist.orm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Plarent on 9/1/2017.
 */

public class ToDoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
