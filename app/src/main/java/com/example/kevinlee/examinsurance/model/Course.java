/*
    课程类
 */
package com.example.kevinlee.examinsurance.model;

/**
 * Created by Kevin Lee on 2018/4/19.
 */

public class Course {
    private String title;
    private String id;
    private int upper_threshold;
    private int lower_threshold;

    public String getTitle(){return title;}
    public void setTitle(String _title){this.title=_title;}
    public String getId(){return id;}
    public void setId(String _id){this.id=_id;}
    public int getUpper_threshold(){return this.upper_threshold;}
    public void setUpper_threshold(int upper){this.upper_threshold=upper;}
    public int getLower_threshold(){return this.lower_threshold;}
    public void setLower_threshold(int lower){this.lower_threshold=lower;}
}
