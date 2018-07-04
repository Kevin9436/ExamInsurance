/*
    学生个人信息活动
 */
package com.example.kevinlee.examinsurance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.adapter.StudentPageColumnAdapter;
import com.example.kevinlee.examinsurance.utils.SharedData;

public class StudentPage_activity extends AppCompatActivity {
    TextView title;
    Button back;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_page);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        back=(Button) findViewById(R.id.user_back);
        title.setText(SharedData.student.getUsername());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //当时脑子一抽就用了个RecycleView列表，实际能装得下，但是这样可以避免出现之后扩展更多功能装不下的情况
        //控件定义见StudentPageColumnAdapter文件
        recyclerView=(RecyclerView) findViewById(R.id.student_page_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StudentPageColumnAdapter adapter=new StudentPageColumnAdapter();
        recyclerView.setAdapter(adapter);
    }
}
