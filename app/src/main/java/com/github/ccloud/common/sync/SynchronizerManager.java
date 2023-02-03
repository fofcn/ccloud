package com.github.ccloud.common.sync;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class SynchronizerManager {

    private final ThreadPoolExecutor threadPoolExecutor = PoolHelper.newFixThreadPool("Synchronizer", 2, "synchronizer-", 20);

    private final ConcurrentHashMap<String, Synchronizer> synchronizerTable = new ConcurrentHashMap<>(2);

    private static volatile SynchronizerManager instance;

    public static SynchronizerManager getInstance() {
        synchronized (SynchronizerManager.class) {
            if (instance == null) {
                instance = new SynchronizerManager();
            }
        }

        return instance;
    }

    public void addSynchronizer(String name, Synchronizer synchronizer) {
        synchronizerTable.putIfAbsent(name, synchronizer);
    }

    public void start() {
        for (Map.Entry<String, Synchronizer> synchronizerEntry : synchronizerTable.entrySet()) {
            Log.i(TAG, "start executing synchronizer: " + synchronizerEntry.getKey());
            threadPoolExecutor.execute(synchronizerEntry.getValue());
        }
    }

    public void stop() {
        threadPoolExecutor.shutdownNow();
    }
}
