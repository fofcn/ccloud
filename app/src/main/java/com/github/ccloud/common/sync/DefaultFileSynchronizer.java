package com.github.ccloud.common.sync;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.github.ccloud.common.collection.CollectionUtil;
import com.github.ccloud.common.sync.fetcher.FileFetchManager;
import com.github.ccloud.common.sync.fetcher.FileFetcher;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.meta.FileMetaManager;
import com.github.ccloud.common.sync.meta.SQLiteMetaManager;
import com.github.ccloud.common.sync.meta.dao.FileMetaSQLiteOpenHelper;
import com.github.ccloud.common.sync.progress.Progress;
import com.github.ccloud.common.sync.progress.ProgressManager;
import com.github.ccloud.common.sync.progress.SQLiteProgressManager;
import com.github.ccloud.common.sync.upload.FileUploadWrapper;
import com.github.ccloud.common.sync.upload.FileUploader;
import com.github.ccloud.util.ContextHolder;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultFileSynchronizer extends DelayManager implements FileSynchronizer {

    private ProgressManager progressManager;

    private FileMetaManager fileMetaManager;

    private FileFetcher fileFetcher;

    private FileUploader fileUploader;

    private FileFetchManager fileFetchManager;

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    private final Semaphore progressSemaphore = new Semaphore(1);

    private final ThreadPoolExecutor masterExecutor = PoolHelper.newSingleThreadPool("FileSynchronizer", "file-sync-master-", 128);

    private final ThreadPoolExecutor fileUploadExecutor = PoolHelper.newFixedPool("FileSynchronizer", "file-sync-worker-", 2, 128);

    public DefaultFileSynchronizer() {
        super(TimeUnit.SECONDS);
        FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper = new FileMetaSQLiteOpenHelper(ContextHolder.getContext());
        this.fileMetaManager = new SQLiteMetaManager(fileMetaSQLiteOpenHelper);
        this.progressManager = new SQLiteProgressManager(fileMetaSQLiteOpenHelper);
    }

    // --------------------------------
    // 同步： 启动数据库扫描：查询上次未同步列表
    // 如果存在则直接启动文件同步
    // --------------------------------
    // 获取最后一次拉取进度
    // 调用用户自定义文件拉取器拉取文件列表
    // 存储到数据库
    // 通知文件上传
    // --------------------------------
    // 文件上传算法
    // --------------------------------
    // 拉取到的文件列表调用用户自定义上传实现进行文件上传
    // 上传成功则通知进度管理器进行进度更新
    // 上传失败进行定时重试
    @Override
    public void sync() {

    }

    @Override
    public boolean init() {
        this.fileFetchManager = new FileFetchManager(this.fileFetcher, fileMetaManager);
        this.fileFetchManager.init();
        this.fileUploader = new FileUploadWrapper(this.fileUploader, fileMetaManager);
        return true;
    }

    @Override
    public void start() {
        fileFetchManager.start();
        startSync();
    }

    @Override
    public void stop() {
        isStarted.set(false);
        masterExecutor.shutdown();
        fileUploadExecutor.shutdown();
    }

    private void startSync() {
        if (isStarted.compareAndSet(false, true)) {
            Runnable main = () -> {
                do {
                    try {
                        progressSemaphore.acquire();
                        Progress progress = progressManager.getProgress();
                        Log.i(TAG, "Current offset: " + progress.getLastOffset());
                        List<FileMeta> fileMetaList = fileMetaManager.getFileMetaList(progress);
                        if (CollectionUtil.isNotEmpty(fileMetaList)) {
                            Log.i(TAG, "Get file meta list from meta manager, file meta size: " + fileMetaList.size());
                            CountDownLatch processLatch = new CountDownLatch(fileMetaList.size());
                            execUpload(fileMetaList, progress.getLastOffset(), processLatch);
                            processLatch.await();
                            progressManager.saveProgress(progress.getLastOffset() + fileMetaList.size());
                            progressSemaphore.release();
                        } else {
                            Log.i(TAG, "No file meta list found, go to sleep");
                            sleep();
                            progressSemaphore.release();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                    }

                } while (isStarted.get());
            };

            masterExecutor.execute(main);
        }
    }

    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    public void setFileFetcher(FileFetcher fileFetcher) {
        this.fileFetcher = fileFetcher;
    }

    private void execUpload(List<FileMeta> fileMetaList, long lastOffset, CountDownLatch processLatch) {
        for (FileMeta fileMeta : fileMetaList) {
            fileUploadExecutor.execute(() -> {
                try {
                    fileUploader.upload(fileMeta);
                } catch (Throwable e) {
                    e.printStackTrace();//todo 异常处理
                    retryUpload();
                } finally {
                    processLatch.countDown();
                    Log.i(TAG, "Finished uploading file to server, file name: " + fileMeta.getName());
                }
            });
        }
    }

    /**
     * 重试上传
     * 第一次立即重试
     * 后续慢速重试
     */
    private void retryUpload() {
    }
}
