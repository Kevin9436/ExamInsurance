package com.example.kevinlee.examinsurance.connectServer.api;

import com.example.kevinlee.examinsurance.connectServer.bean.ApplyRes;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Course;
import com.example.kevinlee.examinsurance.model.Order;
import com.example.kevinlee.examinsurance.model.Student;
import com.example.kevinlee.examinsurance.model.Teacher;
import com.example.kevinlee.examinsurance.model.Teaching;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Kevin Lee on 2018/5/9.
 */

public interface InterfaceManager {

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/student/register")
    Call<BasicCallModel<Student>> studentRegisterReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/teacher/register")
    Call<BasicCallModel<Teacher>> teacherRegisterReq(@Body RequestBody body);

    @GET("user/student/login")
    Call<BasicCallModel<Student>> studentLoginReq(@Query("id") String id, @Query("pw") String pw);

    @GET("user/teacher/login")
    Call<BasicCallModel<Teacher>> teacherLoginReq(@Query("id") String id, @Query("pw") String pw);

    @GET("course/list")
    Call<BasicCallModel<List<Course>>> courselistReq();

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/student/purchase")
    Call<BasicCallModel<Order>> purchaseReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/student/apply")
    Call<BasicCallModel<ApplyRes>> applyReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/teacher/addCourse")
    Call<BasicCallModel<Teaching>> addCourseReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/changeUsername")
    Call<BasicCallModel<String>> changeUsernameReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/changePassword")
    Call<BasicCallModel<String>> changePasswordReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/changePhone")
    Call<BasicCallModel<String>> changePhoneReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("user/teacher/upload")
    Call<BasicCallModel<String>> uploadReq(@Body RequestBody body);
}
