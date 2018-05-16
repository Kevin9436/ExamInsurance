package com.example.kevinlee.examinsurance.connectServer.api;

import retrofit2.Retrofit;

/**
 * Created by Kevin Lee on 2018/5/9.
 */

public class RequestBuilder {
    //添加服务器地址
    public static String BASE_URL="http://www.baidu.com";
    private static InterfaceManager api;
    static volatile Retrofit retrofit=null;

    public static InterfaceManager buildRequest(){
        if(retrofit==null){
            synchronized (RequestBuilder.class){
                if(retrofit==null){
                    retrofit=new Retrofit.Builder().baseUrl(BASE_URL).build();
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
