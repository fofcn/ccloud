package com.github.ccloud.common.sync;

import com.github.ccloud.common.sync.fetcher.FileFetcher;
import com.github.ccloud.common.sync.upload.FileUploader;

public interface FileSynchronizer extends LifeCycle {

    void sync();

    void setFileUploader(FileUploader fileUploader);

    void setFileFetcher(FileFetcher fileFetcher);
}
