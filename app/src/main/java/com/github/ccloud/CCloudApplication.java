package com.github.ccloud;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.github.ccloud.common.sync.SynchronizerManager;
import com.github.ccloud.ui.photo.sync.PhotoSynchronizer;
import com.github.ccloud.ui.video.sync.VideoSynchronizer;
import com.github.ccloud.util.ContextHolder;

public class CCloudApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());

        SynchronizerManager.getInstance().addSynchronizer("Photo", new PhotoSynchronizer());
        SynchronizerManager.getInstance().addSynchronizer("Video", new VideoSynchronizer());
        SynchronizerManager.getInstance().start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SynchronizerManager.getInstance().stop();
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
