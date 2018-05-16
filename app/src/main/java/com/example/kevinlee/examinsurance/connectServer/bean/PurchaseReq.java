package com.example.kevinlee.examinsurance.connectServer.bean;

import com.example.kevinlee.examinsurance.model.Order;

/**
 * Created by Kevin Lee on 2018/5/16.
 */

public class PurchaseReq {
    private String studentId;
    private Order order;

    public String getStudentId(){return studentId;}
    public void setStudentId(String _studentId){studentId=_studentId;}
    public Order getOrder(){return order;}
    public void setOrder(Order _order){order=_order;}
}
