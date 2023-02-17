package com.github.ccloud.common.sync.progress;

import com.github.ccloud.common.sync.meta.dao.FileMetaSQLiteOpenHelper;
import com.github.ccloud.common.sync.progress.dao.SyncProgressDao;

import java.util.concurrent.atomic.AtomicReference;

public class SQLiteProgressManager implements ProgressManager {

    private final SyncProgressDao syncProgressDao;

    private AtomicReference<Long> cachedOffset = new AtomicReference<>(-1L);

    public SQLiteProgressManager(final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper) {
        this.syncProgressDao = new SyncProgressDao(fileMetaSQLiteOpenHelper, "sync_progress");
    }

    @Override
    public Progress getProgress() {
        Progress progress = this.syncProgressDao.getProgress();
        Long oldOffset = cachedOffset.get();
        cachedOffset.compareAndSet(-oldOffset, progress.getLastOffset());
        return progress;
    }

    @Override
    public boolean saveProgress(long lastOffset) {
        cachedOffset.set(lastOffset);
        syncProgressDao.updateProgress(lastOffset);
        return true;
    }
}
