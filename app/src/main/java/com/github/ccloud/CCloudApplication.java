package com.github.ccloud;

import android.app.Application;
import android.content.res.Configuration;

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

        // 配置Http API 工厂
        HttpApiFactory.getInstance().setOkHttpClient(HttpClient.create()).setConverterFactory(GsonConverterFactory.create());
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
}
