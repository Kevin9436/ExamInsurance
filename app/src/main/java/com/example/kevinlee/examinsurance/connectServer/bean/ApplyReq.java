/*
    学生申诉保险请求的封装
 */
package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by Kevin Lee on 2018/5/16.
 */

public class ApplyReq {
    private String studentId;
    private String courseId;

    public String getSudentId(){return studentId;}
    public void setSudentId(String _studentId){studentId=_studentId;}
    public String getCourseId(){return courseId;}
    public void setCourseId(String _courseId){courseId=_courseId;}
}
