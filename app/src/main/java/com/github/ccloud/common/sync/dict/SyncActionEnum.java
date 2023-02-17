package com.github.ccloud.common.sync.dict;

public enum SyncActionEnum {
    ADD(1),
    MODIFY(2),
    DELETE(3);

    private int code;

    SyncActionEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
