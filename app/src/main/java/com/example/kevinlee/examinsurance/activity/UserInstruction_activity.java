/*
    显示用户指南的活动
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

public class UserInstruction_activity extends AppCompatActivity {
    TextView title;
    TextView instruction;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_instruction);
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
        if(SharedData.identity==1) {
            instruction.setText("欢迎使用！\n" +
                    "1. 同学可在课程列表界面选择课程进行投保，投保时必须选择两种形式（高分奖励、低分赔偿）之一进行投保，暂时投保金额固定为1元。\n" +
                    "2. 高分奖励规则：若最后验证分数高于奖励分数则服务器会对你给予投保金额的五倍进行奖励。\n" +
                    "3. 低分奖励规则：若最后验证分数低于赔偿分数则服务器会对你给予投保金额的三倍进行赔偿。\n" +
                    "4. 同学在出成绩后需登录自己账户并在投保记录中向服务器验证才能获得保险回报。\n" +
                    "\n祝大家好好学习，天天向上 ：）");
        }
        else{
            instruction.setText("欢迎使用！\n"+
                    "1. 教师可在主界面的教授课程中浏览或添加自己的授课，务必填写正确课号和课程名称。\n"+
                    "2. 教师在考试出成绩后应尽快上传所授课程的成绩单，请务必选择正确的文件上传。\n"+
                    "3. 上传的Excel文件格式必须为只有三列没有表头，第一列为课号，第二列为学生学号，第三列为最终分数。\n"+
                    "\n祝老师们工作顺利 ：）");
        }
    }
}
