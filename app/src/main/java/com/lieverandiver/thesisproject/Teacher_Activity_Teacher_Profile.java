package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.SubjectAdapter;
import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

@Deprecated
public class Teacher_Activity_Teacher_Profile extends AppCompatActivity implements
        SubjectAdapter.OnViewClickListener{

    private TextView txtvName;
    private TextView txtvDept;
    private Button buttonback;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(getIntent().getExtras().getLong("teacherId"));
        } catch (Exception e) {
            e.printStackTrace();
            teacher = new Teacher();
        }
        setContentView(R.layout.teacher_activity_view_teacher_profile);

        txtvName = (TextView)findViewById(R.id.txtv_fullname);
        txtvDept = (TextView)findViewById(R.id.txtv_dept);
        buttonback = (Button)findViewById(R.id.add_backsubject);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_view_subject);

        SubjectServiceImpl subjectService = new SubjectServiceImpl();
        SubjectAdapter subjectAdapter = null;
        try {
        subjectAdapter =
                new SubjectAdapter(this, subjectService.getSubjectListByTeacherId(teacher.getId()));
        } catch (SubjectException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(subjectAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        txtvName.setText(teacher.getFirstName() + " " + teacher.getMiddleName().substring(0, 1)
                + ". " + teacher.getLastName());
        txtvDept.setText(teacher.getDepartment() != null ? teacher.getDepartment().getName() : "None");
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              finish();
            }
        });
    }

    @Override
    public void viewSubject(Subject subject) {
        Intent intent = new Intent(this, SubjectViewActivity.class);
        intent.putExtra("subjectId", subject.getId());
        startActivity(intent);
    }
}
