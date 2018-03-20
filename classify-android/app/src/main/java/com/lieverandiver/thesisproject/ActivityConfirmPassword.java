package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;
import com.remswork.project.alice.service.impl.UserService;

public class ActivityConfirmPassword extends AppCompatActivity implements View.OnClickListener {


    private Button btnConfirm;
    private Button btnCancel;
    private EditText txPassword;
    private TextView status;
    private TeacherServiceImpl teacherService;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmpassword);


        btnConfirm = findViewById(R.id.activityt_confirmpassword_button);
        btnCancel = findViewById(R.id.activityt_confirmpassword_button_cancel);
        txPassword = findViewById(R.id.activity_confirmpassword_text);
        status = findViewById(R.id.message_status_confirm);
        teacherService = new TeacherServiceImpl();
        try {
            teacher = teacherService.getTeacherById(getIntent().getExtras().getLong("teacherId"));
        } catch (Exception e) {
            e.printStackTrace();
            teacher = new Teacher();
        }
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activityt_confirmpassword_button : {
                if (!TextUtils.isEmpty(txPassword.getText())) {
                    if (txPassword.getText().toString().equals(teacher.getUserDetail().getPassword())) {
                        final Intent intent = new Intent(this, ActivityChangePassword.class);
                        intent.putExtra("teacherId", teacher.getId());
                        startActivity(intent);
                        finish();
                    } else {
                        status.setText("Password not match");
                    }
                } else {
                    status.setText("Please enter your password");
                }
                break;
            }
            default: {
                finish();
            }
        }
    }
}
