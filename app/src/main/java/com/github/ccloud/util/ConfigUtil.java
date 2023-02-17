package com.github.ccloud.util;

import com.github.ccloud.constant.HostConstant;

public class ConfigUtil {

    public static void saveHost(final String host) {
        SpUtil.getInstance().save(HostConstant.HOST_ADDRESS_KEY, host);
    }

    public static String getHost() {
        return SpUtil.getInstance().getString(HostConstant.HOST_ADDRESS_KEY, "");
    }
}
