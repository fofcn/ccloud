package com.github.ccloud.common.sync.progress;

public class SpProgressManager implements ProgressManager {

    @Override
    public Progress getProgress() {

        return null;
    }

    @Override
    public boolean saveProgress(long lastOffset) {
        return false;
    }
}
