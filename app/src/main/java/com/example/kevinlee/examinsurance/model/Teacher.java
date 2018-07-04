/*
    教师类
 */
package com.example.kevinlee.examinsurance.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheny on 2018/5/27.
 */

public class Teacher {
    private String id;
    private String password;
    private String username;
    private String phone;
    private List<Teaching> teachingList;

    public String getId(){return id;}
    public void setId(String _id){this.id=_id;}
    public String getPassword(){return password;}
    public void setPassword(String pw){this.password=pw;}
    public String getUsername(){return this.username;}
    public void setUsername(String _username){this.username=_username;}
    public String getPhone(){return phone;}
    public void setPhone(String phone_num){this.phone=phone_num;}
    public List<Teaching> getTeachingList(){return this.teachingList;}
    public void setTeachingList(List<Teaching> teach){this.teachingList=teach;}
    //登记课程
    public void add_course(Teaching course){
        if(teachingList==null){
            teachingList=new ArrayList<Teaching>();
        }
        teachingList.add(course);
    }
    //上传成绩单后修改状态
    public void changeCourse_status(String id){
        for(int i=0;i<teachingList.size();i++){
            if(teachingList.get(i).getCourse_id().equals(id)){
                teachingList.get(i).changeStatus();
                break;
            }
        }
    }

}
