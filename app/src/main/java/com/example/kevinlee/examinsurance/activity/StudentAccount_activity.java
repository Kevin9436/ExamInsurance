package com.example.kevinlee.examinsurance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(StudentAccount_activity.this,"尚未开放",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
