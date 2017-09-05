package com.enterprise.plarent.todoist.orm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.enterprise.plarent.todoist.model.Project;

/**
 * Created by Plarent on 9/1/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
