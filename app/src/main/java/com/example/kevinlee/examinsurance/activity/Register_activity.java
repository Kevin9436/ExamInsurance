package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.connectServer.bean.RegisterReq;
import com.example.kevinlee.examinsurance.model.BasicActivity;
import com.example.kevinlee.examinsurance.model.Student;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_activity extends AppCompatActivity {

    private Button register;
    private EditText id;
    private EditText username;
    private EditText password;
    private EditText phone;
    private RadioGroup identity;
    private RadioButton student;
    private RadioButton teacher;
    private ProgressDialog registering;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        register = (Button) findViewById(R.id.register_register);
        id = (EditText) findViewById(R.id.register_id_input);
        username = (EditText) findViewById(R.id.register_username_input);
        password = (EditText) findViewById(R.id.register_pw_input);
        phone = (EditText) findViewById(R.id.register_phone_input);
        identity = (RadioGroup) findViewById(R.id.register_identity_group);
        student = (RadioButton) findViewById(R.id.register_student);
        teacher = (RadioButton) findViewById(R.id.register_teacher);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int reg_identity=0;
                if(student.isChecked())
                    reg_identity=1;
                if(teacher.isChecked())
                    reg_identity=2;
                String reg_id=id.getText().toString();
                String reg_username=username.getText().toString();
                String reg_pw=password.getText().toString();
                String reg_phone=phone.getText().toString();
                if(check_valid(reg_identity,reg_id,reg_username,reg_pw,reg_phone)){
                    RegisterReq req = new RegisterReq();
                    req.setIdentity(reg_identity);
                    req.setId(reg_id);
                    req.setUsername(reg_username);
                    req.setPw(reg_pw);
                    req.setPhone(reg_phone);
                    register_action(req);
                }
            }
        });
    }

    private void register_action(final RegisterReq req){
        registering = ProgressDialog.show(Register_activity.this,
                    "注册中","请稍等...",true,false);
        if(Netutils.isNetworkConnected(Register_activity.this)){
            if(req.getIdentity()==1){
                Gson gson=new Gson();
                String route=gson.toJson(req);
                RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
                Call<BasicCallModel<Student>> cb=RequestBuilder.buildRequest().registerReq(body);
                cb.enqueue(new Callback<BasicCallModel<Student>>() {
                    @Override
                    public void onResponse(Call<BasicCallModel<Student>> call, Response<BasicCallModel<Student>> response) {
                        if(response.raw().code()==200){
                            if(response.body().errno==0){
                                SharedData.student=response.body().data;
                                SharedData.getCourseList(Register_activity.this);
                                registering.dismiss();
                                Toast.makeText(Register_activity.this,response.body().msg,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register_activity.this,ShopPage_activity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                registering.dismiss();
                                Toast.makeText(Register_activity.this,response.body().msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            registering.dismiss();
                            Toast.makeText(Register_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                        }
                        RequestBuilder.clear();
                    }

                    @Override
                    public void onFailure(Call<BasicCallModel<Student>> call, Throwable t) {
                        registering.dismiss();
                        Toast.makeText(Register_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                //没有jaccount功能因此教师功能暂时不开放，防止乱注册
                registering.dismiss();
                Toast.makeText(Register_activity.this,"抱歉教师功能未开放",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            registering.dismiss();
            Toast.makeText(Register_activity.this, "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    //检查输入有效性
    private boolean check_valid(int _identity,String _id,String _username,String _pw,String _phone) {
        if(_identity!=1 && _identity!=2) {
            Toast.makeText(Register_activity.this, "请选择身份", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(_id.isEmpty()) {
            Toast.makeText(Register_activity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(_username.isEmpty()){
            Toast.makeText(Register_activity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(_pw.isEmpty()){
            Toast.makeText(Register_activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(_phone.isEmpty()||_phone.length()!=11){
            Toast.makeText(Register_activity.this,"请输入有效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
}
