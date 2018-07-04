/*
    教师登录后的活动，显示教师账号信息列表
 */
package com.example.kevinlee.examinsurance.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.adapter.StudentPageColumnAdapter;
import com.example.kevinlee.examinsurance.adapter.TeacherPageColumnAdapter;
import com.example.kevinlee.examinsurance.utils.SharedData;

public class TeacherPage_activity extends AppCompatActivity {
    TextView title;
    Button logout;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teacher_page);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.teacher_page_title);
        title.setText(SharedData.teacher.getUsername());
        logout=(Button) findViewById(R.id.teacher_page_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.identity=0;
                SharedData.teacher=null;
                Intent intent=new Intent(TeacherPage_activity.this,Entrance_activity.class);
                startActivity(intent);
                finish();
            }
        });

        //和学生账号信息列表一样，控件定义见TeacherPageColumnAdapter文件
        recyclerView=(RecyclerView) findViewById(R.id.teacher_page_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TeacherPageColumnAdapter adapter=new TeacherPageColumnAdapter();
        recyclerView.setAdapter(adapter);
    }
}
