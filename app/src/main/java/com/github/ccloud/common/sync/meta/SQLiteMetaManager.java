package com.github.ccloud.common.sync.meta;

import com.github.ccloud.common.collection.CollectionUtil;
import com.github.ccloud.common.sync.meta.dao.FileMetaDao;
import com.github.ccloud.common.sync.meta.dao.FileMetaSQLiteOpenHelper;
import com.github.ccloud.common.sync.progress.Progress;
import com.github.ccloud.util.ContextHolder;

import java.util.List;

public class SQLiteMetaManager implements FileMetaManager {

    private final FileMetaDao fileMetaDao;


    public SQLiteMetaManager(final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper) {
        this.fileMetaDao = new FileMetaDao(fileMetaSQLiteOpenHelper, "file_meta");
    }

    @Override
    public List<FileMeta> getFileMetaList(Progress progress) {
        List<FileMeta> fileMetaList = fileMetaDao.getNotSyncFileMetaList(progress.getLastOffset());
        if (CollectionUtil.isNotEmpty(fileMetaList)) {
            for (FileMeta fileMeta : fileMetaList) {
                if (fileMeta.getSyncVersion() == null) {
                    fileMeta.setSyncVersion(0);
                }
            }
        }

        return fileMetaList;
    }

    @Override
    public boolean saveFileMeta(List<FileMeta> fileMetaList) {
        fileMetaDao.addFileMetaList(fileMetaList);
        return true;
    }

    @Override
    public boolean setSynced(FileMeta fileMeta) {
        fileMetaDao.updateSyncState(fileMeta);
        return false;
    }

}
