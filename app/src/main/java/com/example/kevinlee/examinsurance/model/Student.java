/*
    学生类
 */
package com.example.kevinlee.examinsurance.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Lee on 2018/4/19.
 */

public class Student {
    private String id;
    private String password;
    private String username;
    private String phone;
    private int account;
    private List<Order> history;

    public String getId(){return id;}
    public void setId(String _id){this.id=_id;}
    public String getPassword(){return password;}
    public void setPassword(String pw){this.password=pw;}
    public String getUsername(){return this.username;}
    public void setUsername(String _username){this.username=_username;}
    public String getPhone(){return phone;}
    public void setPhone(String phone_num){this.phone=phone_num;}
    public int getAccount(){return account;}
    public void setAccount(int i){this.account=i;}
    public List<Order> getHistory(){return history;}
    public void setHistory(List<Order> _history){this.history=_history;}

    //充值和支付操作
    public void charge(int coin){this.account+=coin;}
    public boolean purchase(int coin){
        if(this.account>=coin){
            this.account-=coin;
            return true;
        }
        else
            return false;
    }

    //检查购买是否重复
    public boolean check_item(String course_id){
        for(int i=0;i<history.size();i++){
            if(history.get(i).getId().equals(course_id)&&history.get(i).getStatus()==0){
                return true;
            }
        }
        return false;
    }

    //购买增加记录
    public void add_item(Order course){
        if(history==null) {
            history = new ArrayList<Order>();
        }
        history.add(course);
    }
    //完成订单，修改订单状态，修改订单分数
    public void change_score(String course_id,int score){
        for(int i=0;i<history.size();i++){
            if(course_id.equals(history.get(i).getId())){
                history.get(i).changeStatus();
                history.get(i).setScore(score);
                break;
            }
        }
    }
}
