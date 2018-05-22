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
