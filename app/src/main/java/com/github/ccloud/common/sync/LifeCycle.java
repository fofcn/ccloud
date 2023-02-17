package com.github.ccloud.common.sync;

public interface LifeCycle {

    boolean init();

    void start();

    void stop();
}
