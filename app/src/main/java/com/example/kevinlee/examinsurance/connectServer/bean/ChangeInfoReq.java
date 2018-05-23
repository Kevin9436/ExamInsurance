package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by zheny on 2018/5/23.
 */

public class ChangeInfoReq {
    private String student_id;
    private String changeItem;

    public String getStudent_id(){return student_id;}
    public void setStudent_id(String id){student_id=id;}
    public String getChangeItem(){return changeItem;}
    public void setChangeItem(String item){changeItem=item;}
}
