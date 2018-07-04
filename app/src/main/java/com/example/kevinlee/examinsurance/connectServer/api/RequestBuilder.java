/*
    封装建立Retrofit请求的过程，直接返回接口
 */
package com.example.kevinlee.examinsurance.connectServer.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin Lee on 2018/5/9.
 */

public class RequestBuilder {
    //添加服务器地址
    public static String BASE_URL="http://10.162.239.240:8080/ExamInsurance_server/";
    private static InterfaceManager api;
    static volatile Retrofit retrofit=null;

    //建立网络请求，返回封装的端口
    public static InterfaceManager buildRequest(){
        if(retrofit==null){
            synchronized (RequestBuilder.class){
                if(retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        if(api==null){
            synchronized (RequestBuilder.class){
                if(api==null){
                    api=retrofit.create(InterfaceManager.class);
                }
            }
        }
        return api;
    }
    public static void clear(){
        api=null;
        retrofit=null;
    }
}
