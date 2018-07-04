/*
    学生个人金钱账户活动
 */
package com.example.kevinlee.examinsurance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.utils.SharedData;

public class StudentAccount_activity extends AppCompatActivity {
    TextView title;
    Button back;
    TextView account;
    Button charge;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_account);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        title.setText("账户余额");
        back=(Button) findViewById(R.id.user_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        account=(TextView) findViewById(R.id.user_account);
        account.setText("余额："+ SharedData.student.getAccount()+"元");
        charge=(Button) findViewById(R.id.user_charge);

        //链接到支付宝接口，需要去蚂蚁金服申请
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alipay
            }
        });
    }
}
