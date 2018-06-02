package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.connectServer.bean.ChangeInfoReq;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

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
        setContentView(R.layout.layout_user_info);
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
        user_info_identity=(TextView) findViewById(R.id.user_info_identity);
        user_info_username=(TextView) findViewById(R.id.user_info_username);
        user_info_username_edit=(Button) findViewById(R.id.user_info_username_edit);
        user_info_pw_edit=(Button) findViewById(R.id.user_info_pw_edit);
        user_info_phone=(TextView) findViewById(R.id.user_info_phone);
        user_info_phone_edit=(Button) findViewById(R.id.user_info_phone_edit);
        user_info_logout=(Button) findViewById(R.id.user_info_logout);
        if(SharedData.identity==1){
            user_info_id.setText(SharedData.student.getId());
            user_info_identity.setText("学生");
            user_info_username.setText(SharedData.student.getUsername());
            user_info_phone.setText(SharedData.student.getPhone());
        }
        else{
            user_info_id.setText(SharedData.teacher.getId());
            user_info_identity.setText("教师");
            user_info_username.setText(SharedData.teacher.getUsername());
            user_info_phone.setText(SharedData.teacher.getPhone());
        }
        user_info_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.identity=0;
                SharedData.teacher=null;
                SharedData.student=null;
                SharedData.courseList=null;
                Intent intent=new Intent(UserInfo_activity.this,Entrance_activity.class);
                startActivity(intent);
                finish();
            }
        });

        user_info_username_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View v= LayoutInflater.from(UserInfo_activity.this).inflate(R.layout.alertdialog_change_username,null);
                AlertDialog.Builder changeUsername=new AlertDialog.Builder(UserInfo_activity.this);
                changeUsername.setView(v);
                final EditText editUsername=(EditText) v.findViewById(R.id.change_username);
                changeUsername.setTitle("更改用户名");
                changeUsername.setCancelable(false);

                changeUsername.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newUsername=editUsername.getText().toString();
                        if(newUsername==null||newUsername.length()<=0){
                            Toast.makeText(view.getContext(),"请输入有效用户名",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(!Netutils.isNetworkConnected(view.getContext())){
                                Toast.makeText(view.getContext(),"无网络连接",Toast.LENGTH_SHORT).show();
                            }else {
                                final ProgressDialog changing=ProgressDialog.show(view.getContext(),
                                        "修改中", "请等待", true, false);
                                ChangeInfoReq req = new ChangeInfoReq();
                                if(SharedData.identity==1) {
                                    req.setIdentity(1);
                                    req.setId(SharedData.student.getId());
                                }
                                else{
                                    req.setIdentity(2);
                                    req.setId(SharedData.teacher.getId());
                                }
                                req.setChangeItem(newUsername);

                                Gson gson = new Gson();
                                String route = gson.toJson(req);
                                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                                Call<BasicCallModel<String>> cb = RequestBuilder.buildRequest().changeUsernameReq(body);
                                final long starttime=System.nanoTime();
                                cb.enqueue(new Callback<BasicCallModel<String>>() {
                                    @Override
                                    public void onResponse(Call<BasicCallModel<String>> call, Response<BasicCallModel<String>> response) {
                                        long timeconsume=System.nanoTime()-starttime;
                                        Logger.d("change username consume "+timeconsume/1000+" us");
                                        changing.dismiss();
                                        if (response.raw().code() == 200) {
                                            if (response.body().errno == 0) {
                                                if(SharedData.identity==1)
                                                    SharedData.student.setUsername(response.body().data);
                                                else
                                                    SharedData.teacher.setUsername(response.body().data);
                                                user_info_username.setText(response.body().data);
                                                Toast.makeText(view.getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(view.getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(view.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BasicCallModel<String>> call, Throwable t) {
                                        changing.dismiss();
                                        Toast.makeText(view.getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });

                changeUsername.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                changeUsername.show();
            }
        });

        user_info_pw_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View v=LayoutInflater.from(UserInfo_activity.this).inflate(R.layout.alertdialog_change_pw,null);
                AlertDialog.Builder changePw=new AlertDialog.Builder(UserInfo_activity.this);
                changePw.setTitle("更改密码");
                changePw.setView(v);
                changePw.setCancelable(false);
                final EditText get_original_pw=(EditText) v.findViewById(R.id.original_pw);
                final EditText get_new_pw=(EditText) v.findViewById(R.id.new_pw);

                changePw.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String originalPw=get_original_pw.getText().toString();
                        final String newPw=get_new_pw.getText().toString();
                        String pw;
                        if(SharedData.identity==1)
                            pw=SharedData.student.getPassword();
                        else
                            pw=SharedData.teacher.getPassword();
                        if(!originalPw.equals(pw)){
                            Toast.makeText(view.getContext(),"原密码错误",Toast.LENGTH_SHORT).show();
                        }else{
                            if(newPw==null||newPw.length()<=0){
                                Toast.makeText(view.getContext(),"请输入新密码",Toast.LENGTH_SHORT).show();
                            }else{
                                if(!Netutils.isNetworkConnected(view.getContext())){
                                    Toast.makeText(view.getContext(),"无网络连接",Toast.LENGTH_SHORT).show();
                                }else{
                                    final ProgressDialog changing=ProgressDialog.show(view.getContext(),
                                            "修改中", "请等待", true, false);
                                    ChangeInfoReq req = new ChangeInfoReq();
                                    if(SharedData.identity==1) {
                                        req.setId(SharedData.student.getId());
                                        req.setIdentity(1);
                                    }
                                    else{
                                        req.setId(SharedData.teacher.getId());
                                        req.setIdentity(2);
                                    }
                                    req.setChangeItem(newPw);

                                    Gson gson = new Gson();
                                    String route = gson.toJson(req);
                                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                                    Call<BasicCallModel<String>> cb = RequestBuilder.buildRequest().changePasswordReq(body);
                                    final long starttime=System.nanoTime();
                                    cb.enqueue(new Callback<BasicCallModel<String>>() {
                                        @Override
                                        public void onResponse(Call<BasicCallModel<String>> call, Response<BasicCallModel<String>> response) {
                                            long timeconsume=System.nanoTime()-starttime;
                                            Logger.d("change password consume "+timeconsume/1000+" us");
                                            changing.dismiss();
                                            if(response.raw().code()==200){
                                                if(response.body().errno==0){
                                                    if(SharedData.identity==1)
                                                        SharedData.student.setPassword(newPw);
                                                    else
                                                        SharedData.teacher.setPassword(newPw);
                                                    Toast.makeText(view.getContext(),response.body().msg,Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(view.getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(view.getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<BasicCallModel<String>> call, Throwable t) {
                                            changing.dismiss();
                                            Toast.makeText(view.getContext(),"修改失败",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

                changePw.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                changePw.show();
            }
        });
        user_info_phone_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View v=LayoutInflater.from(UserInfo_activity.this).inflate(R.layout.alertdialog_change_phone,null);
                AlertDialog.Builder changePhone=new AlertDialog.Builder(UserInfo_activity.this);
                changePhone.setTitle("更改手机号");
                changePhone.setView(v);
                changePhone.setCancelable(false);
                final EditText get_new_phone=(EditText) v.findViewById(R.id.new_phone);

                changePhone.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String newPhone=get_new_phone.getText().toString();
                        if(newPhone.length()!=11){
                            Toast.makeText(view.getContext(),"请输入正确手机号",Toast.LENGTH_SHORT).show();
                        }else{
                            if(!Netutils.isNetworkConnected(view.getContext())){
                                Toast.makeText(view.getContext(),"无网络连接",Toast.LENGTH_SHORT).show();
                            }else{
                                final ProgressDialog changing=ProgressDialog.show(view.getContext(),
                                        "修改中", "请等待", true, false);
                                ChangeInfoReq req = new ChangeInfoReq();
                                if(SharedData.identity==1) {
                                    req.setIdentity(1);
                                    req.setId(SharedData.student.getId());
                                }
                                else{
                                    req.setIdentity(2);
                                    req.setId(SharedData.teacher.getId());
                                }
                                req.setChangeItem(newPhone);

                                Gson gson = new Gson();
                                String route = gson.toJson(req);
                                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                                Call<BasicCallModel<String>> cb = RequestBuilder.buildRequest().changePhoneReq(body);
                                final long starttime=System.nanoTime();
                                cb.enqueue(new Callback<BasicCallModel<String>>() {
                                    @Override
                                    public void onResponse(Call<BasicCallModel<String>> call, Response<BasicCallModel<String>> response) {
                                        long timeconsume=System.nanoTime()-starttime;
                                        Logger.d("change phone consume "+timeconsume/1000+" us");
                                        changing.dismiss();
                                        if(response.raw().code()==200){
                                            if(response.body().errno==0){
                                                if(SharedData.identity==1)
                                                    SharedData.student.setPhone(newPhone);
                                                else
                                                    SharedData.teacher.setPhone(newPhone);
                                                user_info_phone.setText(newPhone);
                                                Toast.makeText(view.getContext(),response.body().msg,Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(view.getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(view.getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BasicCallModel<String>> call, Throwable t) {
                                        changing.dismiss();
                                        Toast.makeText(view.getContext(),"修改失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });

                changePhone.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                changePhone.show();
            }
        });
    }
}
