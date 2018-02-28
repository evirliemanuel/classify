package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lieverandiver.thesisproject.adapter.AttendanceAdapter;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.service.AttendanceService;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;

import java.util.List;

import static com.lieverandiver.thesisproject.R.id.add_add3;
import static com.lieverandiver.thesisproject.R.id.add_back3;

public class AttendanceAddActivityF extends AppCompatActivity implements AttendanceAdapter.OnClickListener,
        View.OnClickListener {

    private static final String TAG = AttendanceAddActivityF.class.getSimpleName();

    final AttendanceService attendanceService = new AttendanceServiceImpl();
    private ImageView imageView;
    private Button btnBackButton;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutAttendance;
    private ProgressBar progressBar;
    private long classId;
    private long termId;

    private class AttendanceAddThread extends Thread {
        @Override
        public void run() {

            try {
                final List<Attendance> attendanceList = attendanceService.getAttendanceListByClassId(classId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AttendanceAdapter attendanceAdapter =
                                    new AttendanceAdapter(AttendanceAddActivityF.this, attendanceList);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(AttendanceAddActivityF.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            recyclerView.setAdapter(attendanceAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (GradingFactorException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        linearLayoutAttendance = (LinearLayout) findViewById(add_add3);
        recyclerView = (RecyclerView) findViewById(R.id.add_recycler3);
        btnBackButton = (Button) findViewById(R.id.add_back3);

        linearLayoutAttendance.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progres_man_att);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_add_attendance);
        try {
            classId = getIntent().getExtras().getLong("classId");
            termId = getIntent().getExtras().getLong("termId");

            init();
            new AttendanceAddThread().start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Attendance attendance, long attendanceId) {
        Intent intent = getIntent();
        intent.putExtra("attendanceId", attendanceId);
        intent.setClass(this, AttendanceResultViewActivityF.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case add_add3:
                Intent intent = getIntent().setClass(this, AttendanceInputActivityF.class);
                startActivity(intent);
                break;
            case add_back3:
                intent = getIntent().setClass(this, AttendanceInputActivityF.class);
                startActivity(intent);
                break;
        }
    }
}
