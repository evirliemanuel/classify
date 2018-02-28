package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lieverandiver.thesisproject.fragment.LoginFragment;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private TeacherServiceImpl teacherService;
    private TeacherHelper teacherHelper;
    private ProgressBar progressBar;
    private FrameLayout screen;
    private DoLoginThread doLoginThread;
    private boolean isVaild;
    private String username;
    private String password;

    private class DoLoginThread extends Thread {
        @Override
        public void run() {
            try {
                final List<Teacher> teacherList = teacherService.getTeacherList();
                for(Teacher teacher : teacherList ) {
                    if(teacher.getUserDetail().getUsername().equals(username.trim()) &&
                            teacher.getUserDetail().getPassword().equals(password.trim())) {
                        teacherHelper.saveUser(teacher.getId());

                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.putExtra("teacherId", teacher.getId());
                        isVaild = true;
                        startActivity(intent);
                    }
                }
            } catch (TeacherException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isVaild)
                        Toast.makeText(LoginActivity.this, "Incorrect username or password",
                                Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    screen.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        teacherService = new TeacherServiceImpl();
        teacherHelper = new TeacherHelper(getBaseContext());

        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        screen = (FrameLayout) findViewById(R.id.white_screen_transparent);

        progressBar.setVisibility(View.INVISIBLE);
        screen.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        try {
            if (teacherHelper.loadUser().authenticate()) {
                Intent intent = new Intent(this, MainActivity2.class);
                intent.putExtra("teacherId", teacherHelper.loadUser().get().getId());
                startActivity(intent);
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        teacherService = null;
        teacherHelper = null;
        progressBar = null;
        screen = null;
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void doLogin(final String _username, final String _password) {
        Log.i(TAG, "doLogin");
        progressBar.setVisibility(View.VISIBLE);
        screen.setVisibility(View.VISIBLE);
        username = _username;
        password = _password;

        DoLoginThread doLoginThread = new DoLoginThread();
        doLoginThread.start();
    }
}
