/*
    自定义活动，本应用没有用到，但是这种方法可扩展性比较高
 */
package com.example.kevinlee.examinsurance.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by zheny on 2018/5/19.
 */

public abstract class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        initActivity();
    }

    protected abstract void initActivity();

    protected void showToast(String msg){
        if(msg!=null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
