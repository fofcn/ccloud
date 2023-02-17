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
        SharedPreferences.Editor editor = mSp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public void delete(String key) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.remove(key);
        editor.commit();
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

    // 读取int类型数据
    public Long getLong(String key, long defValue) {
        return mSp.getLong(key, defValue);
    }

}