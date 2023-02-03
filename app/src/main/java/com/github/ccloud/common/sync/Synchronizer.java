package com.github.ccloud.common.sync;

public abstract class Synchronizer implements Runnable {

    @Override
    public void run() {
        doSync();
    }

    protected abstract void doSync();
}
