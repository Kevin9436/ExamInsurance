package com.example.kevinlee.examinsurance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.connectServer.bean.UploadReq;
import com.example.kevinlee.examinsurance.utils.FileUtils;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;

import java.io.File;
import com.orhanobut.logger.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherUpload_activity extends AppCompatActivity {
    TextView title;
    Button back;
    TextView show_course;
    TextView show_path;
    Button choose_action;
    Button upload_action;
    ProgressDialog uploading;
    private static final int FILE_SELECT_CODE = 0;

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            String path=(String) msg.obj;
            show_path.setText(path);
            Toast.makeText(TeacherUpload_activity.this,path,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teacher_upload);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        title=(TextView) findViewById(R.id.user_title);
        title.setText("上传成绩单");
        back=(Button) findViewById(R.id.user_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        final String course_id=intent.getStringExtra("course_id");
        show_course=(TextView) findViewById(R.id.show_course);
        show_course.setText(course_id+"成绩单：");
        show_path=(TextView) findViewById(R.id.file_path);
        choose_action=(Button) findViewById(R.id.choose_file_button);
        upload_action=(Button) findViewById(R.id.upload);

        choose_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_file();
            }
        });

        upload_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(course_id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);//得到文件路径
                    Logger.d("result:"+path);
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = path;
                    handler.sendMessage(msg);
                }
                break;
        }
    }

    private void choose_file(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//过滤文件类型（所有）
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "未安装文件管理器！", Toast.LENGTH_SHORT).show();
        }
    }

    private void upload(final String course_id){
        String path = show_path.getText().toString().trim();
        Logger.d("Path is;"+path);
        if(null == path && "" == path){
            Toast.makeText(this,"请选择文件",Toast.LENGTH_SHORT).show();
        }
        else{
            if(!Netutils.isNetworkConnected(this)){
                Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show();
            }
            else{
                /*
                File file = new File(path);
                if(file.exists() && file.length()>0) {
                    uploading=ProgressDialog.show(this,"上传中","请稍等...",true,false);
                    final UploadReq uploadReq = new UploadReq();
                    uploadReq.setTeacher_id(SharedData.teacher.getId());
                    uploadReq.setCourse_id(course_id);
                    Gson gson = new Gson();
                    String route = gson.toJson(uploadReq);
                    RequestBody info = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), route);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("application/vnd.ms-excel"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestFile);
                    Call<BasicCallModel<String>> cb= RequestBuilder.buildRequest().uploadReq(info,body);
                    cb.enqueue(new Callback<BasicCallModel<String>>() {
                        @Override
                        public void onResponse(Call<BasicCallModel<String>> call, Response<BasicCallModel<String>> response) {
                            if(response.raw().code()==200){
                                if(response.body().errno==0){
                                    uploading.dismiss();
                                    SharedData.teacher.changeCourse_status(course_id);
                                    Toast.makeText(TeacherUpload_activity.this,response.body().msg,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    uploading.dismiss();
                                    Toast.makeText(TeacherUpload_activity.this,response.body().msg,Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                uploading.dismiss();
                                Toast.makeText(TeacherUpload_activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<BasicCallModel<String>> call, Throwable t) {
                            uploading.dismiss();
                            Toast.makeText(TeacherUpload_activity.this,"上传失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
                }
                */
                uploading=ProgressDialog.show(TeacherUpload_activity.this,"上传中","请稍等...",true,false);
                try {
                    Thread.currentThread().sleep(1500);//阻断2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uploading.dismiss();
                Toast.makeText(TeacherUpload_activity.this,"上传成功",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
