/*
    学生登录后的活动，显示商品，并且可跳转到个人信息活动
 */
package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.adapter.CourseAdapter;
import com.example.kevinlee.examinsurance.utils.SharedData;

public class ShopPage_activity extends AppCompatActivity {
    Button shop_page_user;
    Button shop_page_logout;
    Button shop_page_refresh;
    RecyclerView recyclerView;
    ProgressDialog refreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_page);
        getSupportActionBar().hide();
        shop_page_user=(Button) findViewById(R.id.shop_page_user);
        shop_page_logout=(Button) findViewById(R.id.shop_page_logout);
        shop_page_refresh=(Button) findViewById(R.id.shop_page_refresh);
        //商品界面用了recycleView控件，控件定义在CourseAdapter文件中
        recyclerView=(RecyclerView) findViewById(R.id.course_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CourseAdapter adapter=new CourseAdapter(SharedData.courseList);
        recyclerView.setAdapter(adapter);

        //跳转到个人信息活动
        shop_page_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShopPage_activity.this,StudentPage_activity.class);
                startActivity(intent);
            }
        });

        //登出，启动入口活动
        shop_page_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.identity=0;
                SharedData.student=null;
                SharedData.courseList=null;
                Intent intent=new Intent(ShopPage_activity.this,Entrance_activity.class);
                startActivity(intent);
                finish();
            }
        });

        //调用SharedData中的getCourseList方法刷新课程列表
        shop_page_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshing=ProgressDialog.show(ShopPage_activity.this,
                        "刷新课程列表中","请稍等...",true,false);
                SharedData.getCourseList(ShopPage_activity.this);
                refreshing.dismiss();
                CourseAdapter adapter=new CourseAdapter(SharedData.courseList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
