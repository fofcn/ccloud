package com.github.ccloud.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xpf on 2017/03/25 :)
 * Function: sp存储的工具类
 */
public class SpUtil {

    private static final String SP= "ccloud_private_data";

    private SpUtil() {
    }

    private static SpUtil instance = new SpUtil();
    private static volatile SharedPreferences mSp = null;

    public static SpUtil getInstance() {
        synchronized (SpUtil.class) {
            if (mSp == null) {
                mSp = ContextHolder.getContext().getSharedPreferences(SP, Context.MODE_PRIVATE);
            }
        }
        return instance;
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, Object value) {

        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    // 读取String类型数据
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    // 读取boolean类型数据
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    // 读取int类型数据
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

}