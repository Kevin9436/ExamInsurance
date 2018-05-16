package com.example.kevinlee.examinsurance.connectServer.bean;

/**
 * Created by Kevin Lee on 2018/5/8.
 */

public class LoginReq {
    private int identity;
    private String id;
    private String pw;

    public void setIdentity(int _identity){this.identity=_identity;}
    public int getIdentity(){return this.identity;}
    public void setId(String _id){this.id=_id;}
    public String getId(){return this.id;}
    public void setPw(String _pw){this.pw=_pw;}
    public String getPw(){return this.pw;}
}
