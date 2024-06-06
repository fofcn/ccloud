package com.github.ccloud.ui.video.sync;

import com.github.ccloud.common.sync.fetcher.FileFetcher;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.upload.FileUploader;
import com.github.ccloud.common.sync.upload.UploadResult;

import java.util.List;

public class VideoFileSynchronizer implements FileFetcher, FileUploader {

    @Override
    public List<FileMeta> fetch(int pageSize, int offset) {
        return null;
    }

    @Override
    public UploadResult upload(FileMeta fileMeta) {
        return null;
    }
}
