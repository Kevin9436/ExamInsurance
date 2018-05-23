package com.example.kevinlee.examinsurance.connectServer.api;

import com.example.kevinlee.examinsurance.connectServer.bean.ApplyRes;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Course;
import com.example.kevinlee.examinsurance.model.Order;
import com.example.kevinlee.examinsurance.model.Student;

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
    @POST("student/register")
    Call<BasicCallModel<Student>> registerReq(@Body RequestBody body);

    @GET("student/login")
    Call<BasicCallModel<Student>> loginReq(@Query("id") String id, @Query("pw") String pw);

    @GET("course/list")
    Call<BasicCallModel<List<Course>>> courselistReq();

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("student/purchase")
    Call<BasicCallModel<Order>> purchaseReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("student/apply")
    Call<BasicCallModel<ApplyRes>> applyReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("student/changeUsername")
    Call<BasicCallModel<String>> changeUsernameReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("student/changePassword")
    Call<BasicCallModel<String>> changePasswordReq(@Body RequestBody body);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("student/changePhone")
    Call<BasicCallModel<String>> changePhoneReq(@Body RequestBody body);
}
