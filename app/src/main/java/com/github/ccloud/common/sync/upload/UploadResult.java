package com.github.ccloud.common.sync.upload;

public class UploadResult {

    private boolean success;

    private String errCode;

    public UploadResult() {

    }

    public UploadResult(final UploadResult uploadResult) {
        this.success = uploadResult.success;
        this.errCode = uploadResult.getErrCode();
    }

    public UploadResult(boolean isSuccess, String errCode) {
        this.success = isSuccess;
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public static UploadResult success() {
        return new UploadResult(true, "");
    }

    public static UploadResult failed(String errCode) {
        return new UploadResult(false, errCode);
    }
}
