package com.github.ccloud.view.photo;

import com.github.ccloud.common.sync.DefaultFileSynchronizer;
import com.github.ccloud.common.sync.FileSynchronizer;
import com.github.ccloud.common.sync.PoolHelper;
import com.github.ccloud.view.photo.sync.PhotoFileSynchronizer;

import java.util.concurrent.ThreadPoolExecutor;

public class Synchronizer {

    private static volatile Synchronizer instance;

    private final ThreadPoolExecutor executor = PoolHelper.newSingleThreadPool("Synchronizer", "app-sync-", 2);

    private final FileSynchronizer fileSynchronizer;

    public Synchronizer() {
         this.fileSynchronizer = new DefaultFileSynchronizer();
    }

    public static synchronized Synchronizer getInstance() {
        if (instance == null) {
            instance = new Synchronizer();
        }

        return instance;
    }

    public void startSync() {
        // 检查启动条件
        // 1. 具有Storage读写权限
        // 2. 网络已连接
        // 3. 已经配置了服务器连接
        // 4. token有效
        executor.execute(() -> {
            // 同步上传
            PhotoFileSynchronizer photoFileSynchronizer = new PhotoFileSynchronizer();
            fileSynchronizer.setFileFetcher(photoFileSynchronizer);
            fileSynchronizer.setFileUploader(photoFileSynchronizer);
            fileSynchronizer.init();
            fileSynchronizer.start();

        });
    }

    public void stopSync() {
        fileSynchronizer.stop();
        executor.shutdown();
    }
}
