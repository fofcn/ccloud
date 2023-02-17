package com.github.ccloud.common.sync.upload;

import com.github.ccloud.common.sync.meta.FileMeta;

public interface FileUploader {

    UploadResult upload(FileMeta fileMeta);
}
