/*
    对服务器响应进行统一封装，便于客户端识别
 */
package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by Kevin Lee on 2018/5/9.
 */

public class BasicCallModel<T> {
    public int errno;
    public String msg;
    public T data;

}
