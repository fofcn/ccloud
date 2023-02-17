package com.github.ccloud.common.sync.dict;

public enum SyncResultEnum {
    SUCCESS(0);

    private int code;

    SyncResultEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}