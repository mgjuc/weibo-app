package com.myblog.mangojuice.utils;

import android.content.SharedPreferences;

import com.myblog.mangojuice.MyApplication;

public class SharedPrefUtils {

    private static SharedPrefUtils instance;
    private SharedPreferences sp;

    private SharedPrefUtils() {
        sp = MyApplication.getInstance().getSharedPreferences(
                MyApplication.getInstance().getPackageName(), 0);
    }

    //单例
    public static SharedPrefUtils getInstance() {
        if (instance == null) {
            synchronized (SharedPrefUtils.class) {
                if (instance == null) {
                    instance = new SharedPrefUtils();
                }
            }
        }
        return instance;
    }

    public void putStr2SP(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getStrBykey(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void putInt2SP(String key, Integer value) {
        sp.edit().putInt(key, value).commit();
    }

    public Integer getIntByKey(String key, Integer defValue) {
        return sp.getInt(key, defValue);
    }

    public void putFloat2SP(String key, Float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public Float getFloatByKey(String key, Float defValue) {
        return sp.getFloat(key, defValue);
    }

    public void putLong2SP(String key, Long value) {
        sp.edit().putLong(key, value).commit();
    }

    public Long getLongByKey(String key, Long defValue) {
        return sp.getLong(key, defValue);
    }

    public void putBoolean2SP(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public Boolean getBooleanByKey(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void remove(String key) {
        try {
            sp.edit().remove(key).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
