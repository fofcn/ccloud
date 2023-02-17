package com.github.ccloud.common.sync;

public class ProgressFileSynchronizer extends BaseFileSynchronizer {

    // 保存进度文件同步器
    // 进度保存到SharePerforences

    private final Object progressSaver;

    public ProgressFileSynchronizer(Object progressSaver) {
        this.progressSaver = progressSaver;
    }


}
