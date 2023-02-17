package com.github.ccloud.common.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DelayManager {

    private final Map<Integer, Long> delayStepTable = new HashMap<>();

    private final TimeUnit delayTimeUnit;

    private volatile int step = 0;

    public DelayManager(final TimeUnit delayTimeUnit) {
        this.delayTimeUnit = delayTimeUnit;
        this.addDefaultTimeUnit();
    }

    protected void sleep() {
        int nextStep = step % (delayStepTable.size() - 1);
        Long time = delayStepTable.get(nextStep);
        try {
            delayTimeUnit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            addStep();
        }
    }

    protected void addTimeUnit(int stepIdx, long sleep) {
        delayStepTable.put(stepIdx, sleep);
    }

    protected void addDefaultTimeUnit() {
        delayStepTable.put(0, 1L);
        delayStepTable.put(1, 1L);
        delayStepTable.put(2, 2L);
        delayStepTable.put(3, 4L);
        delayStepTable.put(4, 8L);
        delayStepTable.put(5, 16L);
    }

    private void addStep() {
        synchronized (this) {
            step++;
        }
    }

}
