package com.github.ccloud.common.sync.meta;

import java.util.Date;

public class FileMeta {

    private String id;

    private String name;

    private String absPath;

    private Long size;

    private Date fileLastModifyTime;

    private Date createTime;

    private Integer sync;

    private Integer syncAction;

    private Integer syncResult;

    private Date syncTime;

    private Integer syncVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getSyncVersion() {
        return syncVersion;
    }

    public void setSyncVersion(Integer syncVersion) {
        this.syncVersion = syncVersion;
    }

    public Integer getSyncAction() {
        return syncAction;
    }

    public void setSyncAction(Integer syncAction) {
        this.syncAction = syncAction;
    }

    public Integer getSyncResult() {
        return syncResult;
    }

    public void setSyncResult(Integer syncResult) {
        this.syncResult = syncResult;
    }

    public Date getFileLastModifyTime() {
        return fileLastModifyTime;
    }

    public void setFileLastModifyTime(Date fileLastModifyTime) {
        this.fileLastModifyTime = fileLastModifyTime;
    }
}
