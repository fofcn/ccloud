package com.github.ccloud;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.ccloud.common.sync.DefaultFileSynchronizer;
import com.github.ccloud.common.sync.FileSynchronizer;
import com.github.ccloud.http.HttpClient;
import com.github.ccloud.http.factory.HttpApiFactory;
import com.github.ccloud.util.ContextHolder;

import retrofit2.converter.gson.GsonConverterFactory;

public class CCloudApplication extends Application {

    private FileSynchronizer fileSynchronizer;

    public CCloudApplication() {
        this.fileSynchronizer = new DefaultFileSynchronizer();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());

        Log.i("Application", "set global exception handler");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

            }
        });
        Log.i("Application", "set global exception handler");

        Log.i("Application", "application is ready to init");
        // 初始化配置HTTP工厂
        HttpApiFactory.getInstance().setOkHttpClient(HttpClient.create()).setConverterFactory(GsonConverterFactory.create());
        Log.i("Application", "application initialization has completed");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        fileSynchronizer.stop();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void handleUncaughtException(Thread t, Throwable e) {
        Log.e("Application", "Uncaught exception in thread: " + t.getName(), e);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
