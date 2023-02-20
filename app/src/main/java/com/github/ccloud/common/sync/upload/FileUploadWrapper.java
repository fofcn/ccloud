package com.github.ccloud.common.sync.upload;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.github.ccloud.common.sync.dict.SyncEnum;
import com.github.ccloud.common.sync.dict.SyncResultEnum;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.meta.FileMetaManager;

import java.util.Date;

public class FileUploadWrapper implements FileUploader {

    private final FileUploader fileUploader;

    private final FileMetaManager fileMetaManager;

    public FileUploadWrapper(final FileUploader fileUploader,
                             final FileMetaManager fileMetaManager) {
        this.fileUploader = fileUploader;
        this.fileMetaManager = fileMetaManager;
    }

    @Override
    public UploadResult upload(FileMeta fileMeta) {
        Log.i(TAG, "Start uploading file: " + fileMeta.getName());
        UploadResult serverUploadResult = fileUploader.upload(fileMeta);
        fileMeta.setSyncTime(new Date());
        if (serverUploadResult.isSuccess()) {
            fileMeta.setSync(SyncEnum.DONE.getCode());
            fileMeta.setSyncResult(SyncResultEnum.SUCCESS.getCode());
            fileMeta.setSyncVersion(fileMeta.getSyncVersion() + 1);
        } else {
            fileMeta.setSync(SyncEnum.TODO.getCode());
            fileMeta.setSyncResult(SyncResultEnum.FAILED.getCode());
        }
        Log.i(TAG, "Upload file succeeded: " + serverUploadResult.isSuccess() + ", file name: " + fileMeta.getName());
        boolean isSyncSuccess = fileMetaManager.setSynced(fileMeta);
        return isSyncSuccess ? serverUploadResult : UploadResult.failed("ERR_PROGRESS_UPDATE");
    }
}
