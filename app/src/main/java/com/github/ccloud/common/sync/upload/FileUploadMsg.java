package com.github.ccloud.common.sync.upload;

import java.util.ArrayList;
import java.util.List;

public class FileUploadMsg {

    private List<String> fileIdList;

    public FileUploadMsg() {}

    public FileUploadMsg(String... fileIds) {
        this.fileIdList = new ArrayList<>(fileIds.length);
        for (int i = 0; i < fileIds.length; i++) {
            this.fileIdList.add(fileIds[i]);
        }
    }

    public List<String> getFileIdList() {
        return fileIdList;
    }

    public void setFileIdList(List<String> fileIdList) {
        this.fileIdList = fileIdList;
    }
}
