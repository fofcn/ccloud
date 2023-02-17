package com.github.ccloud.common.sync.meta;

import com.github.ccloud.common.sync.progress.Progress;

import java.util.List;

public interface FileMetaManager {

    List<FileMeta> getFileMetaList(Progress progress);

    boolean saveFileMeta(List<FileMeta> fileMetaList);

    boolean setSynced(FileMeta fileMeta);
}
