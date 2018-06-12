package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by zheny on 2018/6/11.
 */

public class UploadReq {
    private String teacher_id;
    private String course_id;

    public void setTeacher_id(String id){
        teacher_id=id;
    }
    public String getTeacher_id(){
        return teacher_id;
    }
    public void setCourse_id(String id){
        course_id=id;
    }
    public String getCourse_id(){
        return course_id;
    }
}
