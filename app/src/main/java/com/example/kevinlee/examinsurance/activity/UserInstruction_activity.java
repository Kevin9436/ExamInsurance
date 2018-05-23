package com.example.kevinlee.examinsurance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.model.BasicActivity;

public class UserInstruction_activity extends AppCompatActivity {
    TextView title;
    TextView instruction;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_instruction_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        title.setText("使用说明");
        back=(Button) findViewById(R.id.user_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        instruction=(TextView) findViewById(R.id.user_instruction);
        instruction.setText("欢迎使用！\n" +
                            "1. 同学可在课程列表界面选择课程进行投保，投保时必须选择两种形式（高分奖励、低分赔偿）之一进行投保，暂时投保金额固定为1元。\n" +
                            "2. 高分奖励规则：若最后验证分数高于奖励分数则服务器会对你给予投保金额的五倍进行奖励。\n"+
                            "3. 低分奖励规则：若最后验证分数低于赔偿分数则服务器会对你给予投保金额的三倍进行赔偿。\n"+
                            "4. 同学在出成绩后需登录自己账户并在投保记录中向服务器验证才能获得保险回报。\n" +
                            "\n祝大家好好学习，天天向上 ：）");
    }
}
