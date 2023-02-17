package com.github.ccloud.util;

import com.github.ccloud.constant.HostConstant;

public class AuthUtil {

    public static void saveToken(String token) {
        SpUtil.getInstance().save(HostConstant.ACCOUNT_TOKEN_KEY, token);
    }

    public static void deleteToken() {
        SpUtil.getInstance().delete(HostConstant.ACCOUNT_TOKEN_KEY);
    }

    public static String getToken() {
        return SpUtil.getInstance().getString(HostConstant.ACCOUNT_TOKEN_KEY, "");
    }

    public static void saveUserId(Long userId) {
        SpUtil.getInstance().save(HostConstant.ACCOUNT_USER_ID_KEY, userId);
    }

    public static Long getUserId() {
        return SpUtil.getInstance().getLong(HostConstant.ACCOUNT_USER_ID_KEY, -1L);
    }
}
