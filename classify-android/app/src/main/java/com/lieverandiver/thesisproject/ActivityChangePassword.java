package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

import io.classify.DI;
import io.classify.model.User;
import io.classify.service.UserService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ActivityChangePassword extends AppCompatActivity implements View.OnClickListener {

    private EditText text1;
    private EditText text2;

    private Button button;
    private Button btnCancel;

    private TextView textView;

    private UserService userService;

    private Retrofit retrofit;

    private Teacher teacher;

    private TeacherServiceImpl teacherService;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_changepassword);

        text1 = findViewById(R.id.activity_changepassword_text1);
        text2 = findViewById(R.id.activity_changepassword_text2);
        button = findViewById(R.id.activityt_changepassword_button);
        btnCancel = findViewById(R.id.activityt_changepassword_button_cancel);

        progressBar = findViewById(R.id.progressbar_cp);

        button.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        retrofit = new DI().getRetrofit();
        userService = retrofit.create(UserService.class);
        teacherService = new TeacherServiceImpl();
        progressBar.setVisibility(View.INVISIBLE);
        try {
            teacher = teacherService.getTeacherById(getIntent().getExtras().getLong("teacherId"));
        } catch (Exception e) {
            e.printStackTrace();
            teacher = new Teacher();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activityt_changepassword_button : {
                if (TextUtils.isEmpty(text1.getText())) {
                    text1.setError("Required");
                    return;
                }
                if (!text1.getText().toString().matches("^[0-9a-zA-Z]+$")) {
                    text1.setError("Found invalid character");
                    return;
                }
                int size= text1.getText().toString().length();
                if (!(size >= 8 && size <= 16)) {
                    text1.setError("8-16 number of characters");
                    return;
                }
                if (TextUtils.isEmpty(text2.getText())) {
                    text2.setError("Required");
                    return;
                }
                if (!text1.getText().toString().equals(text2.getText().toString())) {
                    text2.setError("Not match");
                    return;
                }
                if (teacher != null) {
                    teacher.getUserDetail().setPassword(text1.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    userService.update(teacher.getUserDetail().getId(), teacher.getUserDetail())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<User>() {
                                @Override
                                public void accept(User user) throws Exception {
                                    Log.i("OOOOO", "SAVE");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ActivityChangePassword.this, "Save", Toast.LENGTH_LONG).show();
                                    ActivityChangePassword.this.finish();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.i("OOOOO", "ERROR");
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }
                break;
            }
            default: {
                finish();
            }
        }

    }
}
