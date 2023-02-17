package com.github.ccloud;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.github.ccloud.common.sync.BaseFileSynchronizer;
import com.github.ccloud.common.sync.FileSynchronizer;
import com.github.ccloud.http.HttpClient;
import com.github.ccloud.http.factory.HttpApiFactory;
import com.github.ccloud.util.ContextHolder;
import com.github.ccloud.view.photo.sync.PhotoFileSynchronizer;

import retrofit2.converter.gson.GsonConverterFactory;

public class CCloudApplication extends Application {

    private FileSynchronizer fileSynchronizer;

    public CCloudApplication() {
        this.fileSynchronizer = new BaseFileSynchronizer();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());

        // 配置Http API 工厂
        HttpApiFactory.getInstance().setOkHttpClient(HttpClient.create()).setConverterFactory(GsonConverterFactory.create());

        // 验证token有效性



//        SynchronizerManager.getInstance().addSynchronizer("Photo", new PhotoFileSynchronizer());
//        SynchronizerManager.getInstance().addSynchronizer("Video", new VideoFileSynchronizer());
//        SynchronizerManager.getInstance().start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        SynchronizerManager.getInstance().stop();
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
