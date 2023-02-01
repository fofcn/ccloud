package com.github.ccloud;

import android.app.Application;

import com.github.ccloud.util.ContextHolder;

public class CCloudApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
    }
}
