package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.SubjectAdapter;
import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.SubjectService;
import com.remswork.project.alice.service.TeacherService;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

public class TeacherViewActivity extends AppCompatActivity implements
        SubjectAdapter.OnViewClickListener{

    private static final String TAG = TeacherViewActivity.class.getSimpleName();

    private TeacherService teacherService = new TeacherServiceImpl();
    private SubjectService subjectService = new SubjectServiceImpl();
    private Teacher teacher;;
    private TextView txtName;
    private TextView txtDept;
    private Button btnBack;
    private RecyclerView rView;

    private void init() {
        txtName = (TextView)findViewById(R.id.txtv_fullname);
        txtDept = (TextView)findViewById(R.id.txtv_dept);
        btnBack = (Button)findViewById(R.id.add_backsubject);
        rView = (RecyclerView) findViewById(R.id.recyclerview_view_subject);

        try {
            SubjectAdapter subjectAdapter = new SubjectAdapter(this,
                    subjectService.getSubjectListByTeacherId(teacher.getId()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            rView.setAdapter(subjectAdapter);
            rView.setLayoutManager(layoutManager);
            rView.setItemAnimator(new DefaultItemAnimator());

            txtName.setText(teacher.getFirstName() + " " + teacher.getMiddleName().substring(0, 1)
                    + ". " + teacher.getLastName());
            txtDept.setText(teacher.getDepartment() != null ?
                    teacher.getDepartment().getName() : "None");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    finish();
                }
            });
        }catch (SubjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        try {
            teacher = teacherService.getTeacherById(getIntent().getExtras().getLong("teacherId"));
        } catch (Exception e) {
            e.printStackTrace();
            teacher = new Teacher();
        }
        setContentView(R.layout.teacher_activity_view_teacher_profile);
        init();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        teacherService = null;
        subjectService = null;

        txtName = null;
        txtDept = null;
        btnBack = null;
        rView = null;
    }

    @Override
    public void viewSubject(Subject subject) {
        Intent intent = new Intent(this, SubjectViewActivity.class);
        intent.putExtra("subjectId", subject.getId());
        startActivity(intent);
    }
}
