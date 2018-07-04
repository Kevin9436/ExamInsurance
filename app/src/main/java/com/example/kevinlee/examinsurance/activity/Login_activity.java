/*
    登录活动
 */
package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.connectServer.bean.LoginReq;
import com.example.kevinlee.examinsurance.model.Student;
import com.example.kevinlee.examinsurance.model.Teacher;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_activity extends AppCompatActivity{
    TextView title;
    Button login;
    EditText id;
    EditText pw;
    RadioGroup identity;
    RadioButton student;
    RadioButton teacher;
    ProgressDialog logining;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title = (TextView) findViewById(R.id.title_bar);
        title.setText("登录");

        login = (Button) findViewById(R.id.login_login);
        id = (EditText) findViewById(R.id.login_id_input);
        pw = (EditText) findViewById(R.id.login_pw_input);
        identity = (RadioGroup) findViewById(R.id.login_identity_group);
        student = (RadioButton) findViewById(R.id.login_student);
        teacher = (RadioButton) findViewById(R.id.login_teacher);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int login_identity=0;
                if(student.isChecked())
                    login_identity=1;
                if(teacher.isChecked())
                    login_identity=2;
                String login_id=id.getText().toString();
                String login_pw=pw.getText().toString();
                if(checkValid(login_identity,login_id,login_pw)){
                    LoginReq req=new LoginReq();
                    req.setIdentity(login_identity);
                    req.setId(login_id);
                    req.setPw(login_pw);
                    login_action(req);
                }
            }
        });
    }

    private void login_action(final LoginReq req){
        logining = ProgressDialog.show(Login_activity.this,
                "登陆中","请稍等...",true,false);
        if(Netutils.isNetworkConnected(Login_activity.this)){
            //学生登录
            if(req.getIdentity()==1){
                Call<BasicCallModel<Student>> cb= RequestBuilder.buildRequest().studentLoginReq(req.getId(),req.getPw());
                cb.enqueue(new Callback<BasicCallModel<Student>>() {
                    @Override
                    public void onResponse(Call<BasicCallModel<Student>> call, Response<BasicCallModel<Student>> response) {
                        if(response.raw().code()==200) {
                            //登录成功,准备用户以及课程列表数据
                            if (response.body().errno == 0) {
                                //存数据到SharedData
                                SharedData.identity=1;
                                SharedData.student = response.body().data;
                                SharedData.getCourseList(Login_activity.this);
                                logining.dismiss();
                                Toast.makeText(Login_activity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_activity.this, ShopPage_activity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                logining.dismiss();
                                Toast.makeText(Login_activity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            logining.dismiss();
                            Toast.makeText(Login_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                        }
                        RequestBuilder.clear();
                    }

                    @Override
                    public void onFailure(Call<BasicCallModel<Student>> call, Throwable t) {
                        logining.dismiss();
                        Toast.makeText(Login_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                        Logger.d(t);
                    }
                });
            }
            //老师登录
            else{
                Call<BasicCallModel<Teacher>> cb=RequestBuilder.buildRequest().teacherLoginReq(req.getId(),req.getPw());
                final long starttime=System.nanoTime();
                cb.enqueue(new Callback<BasicCallModel<Teacher>>() {
                    @Override
                    public void onResponse(Call<BasicCallModel<Teacher>> call, Response<BasicCallModel<Teacher>> response) {
                        if(response.raw().code()==200) {
                            //登录成功,准备用户以及课程列表数据
                            if (response.body().errno == 0) {
                                SharedData.identity=2;
                                SharedData.teacher = response.body().data;
                                logining.dismiss();
                                Toast.makeText(Login_activity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_activity.this, TeacherPage_activity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                logining.dismiss();
                                Toast.makeText(Login_activity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            logining.dismiss();
                            Toast.makeText(Login_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                        }
                        RequestBuilder.clear();
                    }

                    @Override
                    public void onFailure(Call<BasicCallModel<Teacher>> call, Throwable t) {
                        logining.dismiss();
                        Toast.makeText(Login_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                        Logger.d(t);
                    }
                });
            }
        }
        else{
            logining.dismiss();
            Toast.makeText(Login_activity.this, "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    //检查登录信息输入是否有效
    private boolean checkValid(int _identity,String _id,String _pw){
        if(_identity!=1&_identity!=2){
            Toast.makeText(Login_activity.this,"请输入身份",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(_id.isEmpty()){
            Toast.makeText(Login_activity.this,"请输入学工号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(_pw.isEmpty()){
            Toast.makeText(Login_activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}