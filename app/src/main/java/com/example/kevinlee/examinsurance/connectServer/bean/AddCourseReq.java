/*
    教师添加课程请求的封装
 */
package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by zheny on 2018/5/28.
 */

public class AddCourseReq {
    private String teacher_id;
    private String course_id;
    private String course_title;

    public String getTeacher_id(){return teacher_id;}
    public void setTeacher_id(String id){teacher_id=id;}
    public String getCourse_id(){return course_id;}
    public void setCourse_id(String id){course_id=id;}
    public String getCourse_title(){return course_title;}
    public void setCourse_title(String title){course_title=title;}
}
