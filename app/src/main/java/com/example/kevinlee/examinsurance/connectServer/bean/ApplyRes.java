package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by Kevin Lee on 2018/5/16.
 */

public class ApplyRes {
    private String courseId;
    private int score;
    private int reward;

    public String getCourseId(){return courseId;}
    public void setCourseId(String _courseId){courseId=_courseId;}
    public int getScore(){return score;}
    public void setScore(int _score){score=_score;}
    public int getReward(){return reward;}
    public void setReward(int _reward){reward=_reward;}
}
