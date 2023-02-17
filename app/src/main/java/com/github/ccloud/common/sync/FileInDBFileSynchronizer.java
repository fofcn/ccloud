package com.github.ccloud.common.sync;

import java.util.List;

public abstract class FileInDBFileSynchronizer extends BaseFileSynchronizer {

    @Override
    public void sync() {
//        // 获取文件列表
//        List<SyncFile> fileList = getFileList();
//        // 添加文件到数据库
//        for (SyncFile syncFile : fileList) {
//            addFileToDB(syncFile);
//        }
//        // 开始上传
    }



    private void addFileToDB(SyncFile syncFile) {

    }

}
