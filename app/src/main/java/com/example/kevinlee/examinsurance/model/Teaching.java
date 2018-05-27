package com.example.kevinlee.examinsurance.model;

/**
 * Created by zheny on 2018/5/27.
 */

public class Teaching {
    private String course_id;
    private String course_title;
    private int status;

    public String getCourse_id(){return this.course_id;}
    public void setCourse_id(String id){this.course_id=id;}
    public String getCourse_title(){return this.course_title;}
    public void setCourse_title(String title){this.course_title=title;}
    public int getStatus(){return status;}
    public void setStatus(int s){this.status=s;}

    public boolean changeStatus(){
        if(status==1)
            return false;
        else{
            status=1;
            return true;
        }
    }
}
