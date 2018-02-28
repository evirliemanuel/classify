package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.AttendanceResultAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.AttendanceResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.AttendanceService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AttendanceResultViewActivityF extends AppCompatActivity {

    private static final String TAG = AttendanceResultViewActivityF.class.getSimpleName();

    private final AttendanceService attendanceService = new AttendanceServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<AttendanceResult> resultList = new ArrayList<>();
    private Attendance attendance;

    private TextView textViewDate;
    private TextView textViewName;
    private RecyclerView recyclerViewView;

    private long classId;
    private long attendanceId;
    private long termId;

    private class AttendanceViewThread extends Thread {
        @Override
        public void run() {
            try {
                attendance = attendanceService.getAttendanceById(attendanceId);
            for (Student s : classService.getStudentList(classId)) {
                AttendanceResult result = attendanceService.getAttendanceResultByAttendanceAndStudentId(attendanceId, s.getId());
                if(result != null)
                    resultList.add(result);
            }
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                            String date = attendance.getDate();
                            String title = attendance.getTitle();

                            textViewDate.setText(date);
                            textViewName.setText(title);

                            AttendanceResultAdapter simpleAttendanceAdapter = new AttendanceResultAdapter(AttendanceResultViewActivityF.this, resultList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(AttendanceResultViewActivityF.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            recyclerViewView.setAdapter(simpleAttendanceAdapter);
                            recyclerViewView.setLayoutManager(layoutManager);
                            recyclerViewView.setItemAnimator(new DefaultItemAnimator());

                    }
                });
            } catch (ClassException| GradingFactorException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        textViewDate = (TextView) findViewById(R.id.date_attendance_xf);
        textViewName = (TextView) findViewById(R.id.name_attendance_xf);
        recyclerViewView = (RecyclerView) findViewById(R.id.recyclerview_attendance_fx);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view_attendance);

        classId = getIntent().getExtras().getLong("classId");
        attendanceId = getIntent().getExtras().getLong("attendanceId");
        termId = getIntent().getExtras().getLong("termId");
        init();
        new AttendanceViewThread().start();
    }
}
