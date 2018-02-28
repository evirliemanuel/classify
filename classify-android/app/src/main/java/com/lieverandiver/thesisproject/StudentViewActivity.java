package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.StudentAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    private ToggleButton btnSearch;
    private Button btnSearchOk;
    private Button btnSearchCancel;
    private EditText editTextSearch;
    private FrameLayout frameLayoutSearch;
    private TextView txtMsgContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_view_student);

        init();
        initRView(getIntent().getExtras().getLong("classId"));
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.student_recyclerview);
        btnSearch = (ToggleButton)findViewById(R.id.btn_search_student);
        btnSearchOk = (Button)findViewById(R.id.btn_search_ok_student);
        btnBack = (Button) findViewById(R.id.btn_back_student);
        editTextSearch =(EditText)findViewById(R.id.etxt_search_student);
        frameLayoutSearch = (FrameLayout)findViewById(R.id.frame_search_student);
        txtMsgContent = (TextView) findViewById(R.id.message_label_stud);

        frameLayoutSearch.setVisibility(View.GONE);
        txtMsgContent.setVisibility(View.INVISIBLE);

        btnSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    frameLayoutSearch.setVisibility(View.VISIBLE);
                else
                    frameLayoutSearch.setVisibility(View.GONE);
            }
        });
    }

    public void initRView(final long classId) {
        final Handler handler = new Handler(getMainLooper());
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final ClassServiceImpl classService =  new ClassServiceImpl();
                    final List<Student> studenList = new ArrayList<>();
                    final Set<Student> studentSet = classService.getStudentList(classId);

                    for(Student student : studentSet)
                        studenList.add(student);

                    final StudentAdapter studentAdapter = new StudentAdapter(
                            StudentViewActivity.this, studenList);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(
                            StudentViewActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(studentAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            if(studenList.size() < 1)
                                txtMsgContent.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (ClassException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
