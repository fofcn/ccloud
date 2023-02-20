package com.github.ccloud.common.sync;

import com.github.ccloud.common.sync.fetcher.FileFetcher;
import com.github.ccloud.common.sync.upload.FileUploader;

public class FileSynchronizerBuilder {

    private FileUploader fileUploader;

    private FileFetcher fileFetcher;


    public FileSynchronizerBuilder fileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
        return this;
    }

    public FileSynchronizerBuilder fileFetcher(FileFetcher fileFetcher) {
        this.fileFetcher = fileFetcher;
        return this;
    }

    public FileSynchronizer build() {
        DefaultFileSynchronizer defaultFileSynchronizer = new DefaultFileSynchronizer();
        defaultFileSynchronizer.setFileFetcher(this.fileFetcher);
        defaultFileSynchronizer.setFileUploader(this.fileUploader);
        return defaultFileSynchronizer;
    }
}
