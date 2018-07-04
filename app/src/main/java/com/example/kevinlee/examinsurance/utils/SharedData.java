/*
    应用全部活动共享的数据
 */
package com.example.kevinlee.examinsurance.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Course;
import com.example.kevinlee.examinsurance.model.Student;
import com.example.kevinlee.examinsurance.model.Teacher;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin Lee on 2018/5/11.
 */

public class SharedData {
    public static int identity;//1 student, 2 teacher
    public static Student student;
    public static Teacher teacher;
    public static List<Course> courseList;

    //从服务器获取课程列表
    public static void getCourseList(final Context context){
        Call<BasicCallModel<List<Course>>> cb= RequestBuilder.buildRequest().courselistReq();
        cb.enqueue(new Callback<BasicCallModel<List<Course>>>() {
            @Override
            public void onResponse(Call<BasicCallModel<List<Course>>> call, Response<BasicCallModel<List<Course>>> response) {
                if(response.raw().code()==200){
                    if(response.body().data!=null){
                        courseList=response.body().data;
                    }
                    else {
                        Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, "获取课程列表失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasicCallModel<List<Course>>> call, Throwable t) {
                Toast.makeText(context, "获取课程列表失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
