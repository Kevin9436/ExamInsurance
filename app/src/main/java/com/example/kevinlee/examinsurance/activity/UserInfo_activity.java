package com.example.kevinlee.examinsurance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.model.BasicActivity;
import com.example.kevinlee.examinsurance.utils.SharedData;

public class UserInfo_activity extends AppCompatActivity {
    TextView title;
    Button back;
    TextView user_info_id;
    TextView user_info_identity;
    TextView user_info_username;
    Button user_info_username_edit;
    Button user_info_pw_edit;
    TextView user_info_phone;
    Button user_info_phone_edit;
    Button user_info_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        title.setText("个人信息");
        back=(Button) findViewById(R.id.user_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        user_info_id=(TextView) findViewById(R.id.user_info_id);
        user_info_id.setText(SharedData.student.getId());
        user_info_identity=(TextView) findViewById(R.id.user_info_identity);
        user_info_identity.setText("学生");
        user_info_username=(TextView) findViewById(R.id.user_info_username);
        user_info_username.setText(SharedData.student.getUsername());
        user_info_username_edit=(Button) findViewById(R.id.user_info_username_edit);
        user_info_pw_edit=(Button) findViewById(R.id.user_info_pw_edit);
        user_info_phone=(TextView) findViewById(R.id.user_info_phone);
        user_info_phone.setText(SharedData.student.getPhone());
        user_info_phone_edit=(Button) findViewById(R.id.user_info_phone_edit);
        user_info_logout=(Button) findViewById(R.id.user_info_logout);
        user_info_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.student=null;
                SharedData.courseList=null;
                Intent intent=new Intent(UserInfo_activity.this,Entrance_activity.class);
                startActivity(intent);
                finish();
            }
        });
        user_info_username_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        user_info_pw_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        user_info_phone_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
