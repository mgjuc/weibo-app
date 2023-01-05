package com.myblog.mangojuice;

import android.app.Application;

///单例
public class MyApplication extends Application {
    private  static MyApplication mApp;

    public static MyApplication getInstance()
    {
        return mApp;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApp = this;    //在打开应用时对静态的应用实例赋值
    }
}
