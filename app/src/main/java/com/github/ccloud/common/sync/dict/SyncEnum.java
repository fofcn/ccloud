package com.github.ccloud.common.sync.dict;

public enum SyncEnum {
    TODO(0),
    DONE(1);

    private int code;

    SyncEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
