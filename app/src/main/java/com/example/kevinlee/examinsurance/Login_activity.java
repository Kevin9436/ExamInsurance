package com.example.kevinlee.examinsurance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Login_activity extends AppCompatActivity{

    private Button login;
    private EditText id;
    private EditText pw;
    private RadioGroup identity;
    private RadioButton student;
    private RadioButton teacher;
    private int user_identity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        login = (Button) findViewById(R.id.login_login);
        id = (EditText) findViewById(R.id.login_id_input);
        pw = (EditText) findViewById(R.id.login_pw_input);
        identity = (RadioGroup) findViewById(R.id.login_identity_group);
        student = (RadioButton) findViewById(R.id.login_student);
        teacher = (RadioButton) findViewById(R.id.login_teacher);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        identity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(student.getId() == checkedId)
                    user_identity = 1;
                if(teacher.getId() == checkedId)
                    user_identity = 2;
            }
        });
    }
}