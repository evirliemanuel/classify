package com.lieverandiver.thesisproject;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.StudentAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.id.toggle;

public class GradeViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    private ToggleButton btnSearch;
    private Button btnSearchOk;
    private Button btnSearchCancel;
    private EditText editTextSearch;
    private FrameLayout frameLayoutSearch;
    private TextView txtMsgContent;
    private ToggleButton toggleButtonMidterm;
    private ToggleButton toggleButtonFinals;
    private LinearLayout linearLayoutMidterm;
    private LinearLayout linearLayoutFinals;
    private RecyclerView recyclerViewMidterm;
    private RecyclerView recyclerViewFinals;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_view_grade);

        init();
        initRView(getIntent().getExtras().getLong("classId"));
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.grade_midterm_recyclerview);
        btnSearch = (ToggleButton)findViewById(R.id.btn_search_grade);
        btnSearchOk = (Button)findViewById(R.id.btn_search_ok_grade);
        btnBack = (Button) findViewById(R.id.btn_back_grade);
        editTextSearch =(EditText)findViewById(R.id.etxt_search_grade);
        frameLayoutSearch = (FrameLayout)findViewById(R.id.frame_search_grade);
        txtMsgContent = (TextView) findViewById(R.id.message_label_stud);

        toggleButtonMidterm = (ToggleButton) findViewById(R.id.grade_toggleMidterm);
        toggleButtonFinals = (ToggleButton) findViewById(R.id.grade_toggleFinals);
        linearLayoutMidterm = (LinearLayout)findViewById(R.id.grade_linearMidterm);
        linearLayoutFinals = (LinearLayout)findViewById(R.id.grade_linearFinals);
        recyclerViewMidterm = (RecyclerView)findViewById(R.id.grade_recyclerMidterm);
        recyclerViewFinals = (RecyclerView)findViewById(R.id.grade_recyclerFinals);
        linearLayoutFinals.setVisibility(View.GONE);
        toggleButtonMidterm.setChecked(true);

        toggleButtonMidterm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutMidterm.setVisibility(View.VISIBLE);
                    linearLayoutFinals.setVisibility(View.GONE);
                    toggleButtonFinals.setChecked(false);
                    Intent intent = new Intent(GradeViewActivity.this, GradeResultActivity2.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        toggleButtonFinals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutFinals.setVisibility(View.VISIBLE);
                    toggleButtonMidterm.setChecked(false);
                    linearLayoutMidterm.setVisibility(View.GONE);
                } else {
                    toggleButtonMidterm.setChecked(true);
                }
            }
        });



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
                            GradeViewActivity.this, studenList);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(
                            GradeViewActivity.this);
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
