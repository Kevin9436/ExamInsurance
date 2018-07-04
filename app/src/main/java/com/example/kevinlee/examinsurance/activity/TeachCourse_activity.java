/*
    教师查看自己所教授课程活动，界面支持添加课程功能
 */
package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.adapter.TeachCourseAdapter;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.AddCourseReq;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Teaching;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachCourse_activity extends AppCompatActivity {
    TextView title;
    Button back;
    Button add_course;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teach_course);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        title.setText("授课列表");
        back=(Button) findViewById(R.id.user_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //所授课程列表用RecycleView控件，控件定义见TeachCourseAdapter文件
        recyclerView=(RecyclerView) findViewById(R.id.teach_course_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TeachCourseAdapter adapter=new TeachCourseAdapter(SharedData.teacher.getTeachingList(),TeachCourse_activity.this);
        recyclerView.setAdapter(adapter);

        //添加课程按钮
        add_course=(Button) findViewById(R.id.add_course);
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                View v= LayoutInflater.from(TeachCourse_activity.this).inflate(R.layout.alertdialog_add_course,null);
                //弹出对话框，让教师填写课程信息
                final AlertDialog.Builder alert_add_course=new AlertDialog.Builder(TeachCourse_activity.this);
                alert_add_course.setTitle("添加授课课程");
                alert_add_course.setView(v);
                alert_add_course.setCancelable(false);
                final EditText id=(EditText) v.findViewById(R.id.get_course_id);
                final EditText title=(EditText) v.findViewById(R.id.get_course_title);
                //对话框的确认按键点击事件
                alert_add_course.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //获取教师输入的课程ID和title
                        String course_id=id.getText().toString();
                        String course_title=title.getText().toString();
                        if(course_id==null||course_id.length()<=0||course_title==null||course_title.length()<=0){
                            Toast.makeText(view.getContext(),"请输入有效课号和课程名称",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(!Netutils.isNetworkConnected(view.getContext())){
                                Toast.makeText(view.getContext(),"无网络连接",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                final ProgressDialog adding=ProgressDialog.show(view.getContext(),
                                        "添加中", "请等待", true, false);
                                AddCourseReq req=new AddCourseReq();
                                req.setTeacher_id(SharedData.teacher.getId());
                                req.setCourse_id(course_id);
                                req.setCourse_title(course_title);

                                Gson gson = new Gson();
                                String route = gson.toJson(req);
                                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                                //发送添加课程网络请求
                                Call<BasicCallModel<Teaching>> cb = RequestBuilder.buildRequest().addCourseReq(body);
                                cb.enqueue(new Callback<BasicCallModel<Teaching>>() {
                                    @Override
                                    public void onResponse(Call<BasicCallModel<Teaching>> call, Response<BasicCallModel<Teaching>> response) {
                                        adding.dismiss();
                                        if(response.raw().code()==200){
                                            if(response.body().errno==0){
                                                SharedData.teacher.add_course(response.body().data);
                                                //添加成功后更新对话框外部的RecycleView列表
                                                TeachCourseAdapter adapter=new TeachCourseAdapter(SharedData.teacher.getTeachingList(),TeachCourse_activity.this);
                                                recyclerView.setAdapter(adapter);
                                                Toast.makeText(view.getContext(),response.body().msg,Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(view.getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(view.getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BasicCallModel<Teaching>> call, Throwable t) {
                                        adding.dismiss();
                                        Toast.makeText(view.getContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
                //对话框的取消按键点击事件
                alert_add_course.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert_add_course.show();
            }
        });
    }
}
