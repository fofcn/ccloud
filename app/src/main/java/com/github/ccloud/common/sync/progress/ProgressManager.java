package com.github.ccloud.common.sync.progress;

public interface ProgressManager {
    Progress getProgress();

    boolean saveProgress(long lastOffset);
}
