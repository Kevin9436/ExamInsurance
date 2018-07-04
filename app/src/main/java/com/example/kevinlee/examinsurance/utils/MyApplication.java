/*
    自定义应用，用这个是为了使用Logger这个日志框架，在这里声明后在任意活动中都可以使用
 */
package com.example.kevinlee.examinsurance.utils;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by zheny on 2018/5/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
