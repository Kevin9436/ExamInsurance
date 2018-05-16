package com.example.kevinlee.examinsurance.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Course;
import com.example.kevinlee.examinsurance.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin Lee on 2018/5/11.
 */

public class SharedData {
    public static Student student;
    public static List<Course> courseList;

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
                        Toast.makeText(context, "获取课程列表失败！", Toast.LENGTH_SHORT).show();
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
