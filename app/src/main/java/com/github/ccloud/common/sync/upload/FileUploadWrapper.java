package com.github.ccloud.common.sync.upload;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.github.ccloud.common.sync.dict.SyncResultEnum;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.meta.FileMetaManager;
import com.github.ccloud.common.sync.progress.ProgressManager;

import java.util.Date;

public class FileUploadWrapper implements FileUploader {

    private final FileUploader fileUploader;

    private final ProgressManager uploadProgressManager;

    private final FileMetaManager fileMetaManager;

    public FileUploadWrapper(final FileUploader fileUploader,
                             final ProgressManager uploadProgressManager,
                             final FileMetaManager fileMetaManager) {
        this.fileUploader = fileUploader;
        this.uploadProgressManager = uploadProgressManager;
        this.fileMetaManager = fileMetaManager;
    }

    @Override
    public UploadResult upload(FileMeta fileMeta) {
        Log.i(TAG, "Start uploading file: " + fileMeta.getName());
        UploadResult serverUploadResult = fileUploader.upload(fileMeta);
        if (serverUploadResult.isSuccess()) {
            fileMeta.setSyncTime(new Date());
            fileMeta.setSyncResult(SyncResultEnum.SUCCESS.getCode());
            fileMeta.setSyncVersion(fileMeta.getSyncVersion() + 1);
            boolean isSyncSuccess = fileMetaManager.setSynced(fileMeta);
            return isSyncSuccess ? serverUploadResult : UploadResult.failed("ERR_PROGRESS_UPDATE");
        } else {
            NotifyUploadFailed(new FileUploadMsg(fileMeta.getId()));
        }

        return serverUploadResult;
    }

    private void NotifyUploadFailed(FileUploadMsg fileUploadMsg) {
    }
}
