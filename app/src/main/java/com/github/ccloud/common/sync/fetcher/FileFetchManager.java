package com.github.ccloud.common.sync.fetcher;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.github.ccloud.common.collection.CollectionUtil;
import com.github.ccloud.common.sync.DelayManager;
import com.github.ccloud.common.sync.dict.SyncActionEnum;
import com.github.ccloud.common.sync.dict.SyncEnum;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.meta.FileMetaManager;
import com.github.ccloud.common.sync.LifeCycle;
import com.github.ccloud.common.sync.PoolHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileFetchManager extends DelayManager implements LifeCycle {

    private volatile int lastOffset = 0;

    private final FileFetcher fileFetcher;

    private final FileMetaManager fileMetaManager;

    private final ThreadPoolExecutor fetchExecutor = PoolHelper.newSingleThreadPool("FileFetch", "file-fetch-", 128);

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    public FileFetchManager(final FileFetcher fileFetcher, final FileMetaManager fileMetaManager) {
        super(TimeUnit.SECONDS);
        this.fileFetcher = fileFetcher;
        this.fileMetaManager = fileMetaManager;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void start() {
        if (isStarted.compareAndSet(false, true)) {
            Runnable fetchRunner = () -> {
                try {
                    do {
                        List<FileMeta> fileMetaList = fileFetcher.fetch(10, lastOffset);
                        if (CollectionUtil.isEmpty(fileMetaList)) {
                            sleep();
                        }
                        fillFileMetaFields(fileMetaList);
                        boolean isSaveSuccess = fileMetaManager.saveFileMeta(fileMetaList);
                        // todo 可以回调给用户， 这里先打印一个日志
                        if (!isSaveSuccess) {
                            Log.e(TAG, "file meta save failed");
                        }

                        lastOffset = lastOffset + fileMetaList.size();
                        // 通知有文件新增
                    } while (isStarted.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } ;

            fetchExecutor.execute(fetchRunner);
        }
    }

    @Override
    public void stop() {
        if (isStarted.compareAndSet(true, false)) {
            fetchExecutor.shutdown();
        }
    }

    public void fillFileMetaFields(List<FileMeta> fileMetaList) {
        for (FileMeta fileMeta: fileMetaList) {
            fileMeta.setId(UUID.randomUUID().toString().replace("-", ""));
            fileMeta.setSync(SyncEnum.TODO.getCode());
            fileMeta.setSyncAction(SyncActionEnum.ADD.getCode());
            fileMeta.setCreateTime(new Date());
            fileMeta.setSyncVersion(0);
        }
    }

}
