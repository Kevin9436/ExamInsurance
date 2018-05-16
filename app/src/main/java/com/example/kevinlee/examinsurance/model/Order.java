package com.example.kevinlee.examinsurance.model;

/**
 * Created by Kevin Lee on 2018/5/15.
 */

public class Order {
    private String course_id;
    private String course_title;
    private int status; //0 for processing, 1 for completed;
    private int type;   //0 for upper-threshold, 1 for lower-threshold;
    private int threshold;
    private int score;

    public String getTitle(){return this.course_title;}
    public void setTitle(String _title){this.course_title=_title;}
    public String getId(){return this.course_id;}
    public void setId(String _id){this.course_id=_id;}
    public int getStatus(){return this.status;}
    public void setStatus(int _status){this.status=_status;}
    public int getType(){return type;}
    public void setType(int _type){this.type=_type;}
    public int getThreshold(){return this.threshold;}
    public void setThreshold(int _threshold){this.threshold=_threshold;}
    public int getScore(){return this.score;}
    public void setScore(int _score){this.score=_score;}
    public boolean changeStatus(){
        if(status==0){
            status=1;
            return true;
        }
        else
            return false;
    }
}
