package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by zheny on 2018/5/23.
 */

public class ChangeInfoReq {
    private int identity;
    private String id;
    private String changeItem;

    public int getIdentity(){return identity;}
    public void setIdentity(int _identity){identity=_identity;}
    public String getStudent_id(){return id;}
    public void setId(String _id){id=_id;}
    public String getChangeItem(){return changeItem;}
    public void setChangeItem(String item){changeItem=item;}
}
