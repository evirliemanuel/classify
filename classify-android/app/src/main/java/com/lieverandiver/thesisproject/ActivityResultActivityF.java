package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.ActivityResultAdapter;
import com.lieverandiver.thesisproject.adapter.ActivityResultAdapterF;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static com.lieverandiver.thesisproject.R.id.btn_back;


public class ActivityResultActivityF extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ActivityResultActivityF.class.getSimpleName();

    private final ActivityService activityService = new ActivityServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<ActivityResult> resultList = new ArrayList<>();
    private Activity activity;

    private TextView textViewDate;
    private TextView textViewName;
    private TextView textViewTotal;
    private RecyclerView recyclerViewView;
    private Button btnBack;

    private long classId;
    private long termId;
    private long activityId;


    private class ActivityViewThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String date = activity.getDate();
                        String title = activity.getTitle();
                        String itemTotal = String.valueOf(activity.getItemTotal());

                        textViewDate.setText(date);
                        textViewName.setText(title);
                        textViewTotal.setText(itemTotal);

                        for (Student s : classService.getStudentList(classId)) {
                            ActivityResult result = activityService.getActivityResultByActivityAndStudentId(activity.getId(), s.getId());
                            if(result != null)
                                resultList.add(result);
                        }

                        ActivityResultAdapterF simpleActivityAdapter = new ActivityResultAdapterF(ActivityResultActivityF.this, resultList, activity.getItemTotal());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityResultActivityF.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerViewView.setAdapter(simpleActivityAdapter);
                        recyclerViewView.setLayoutManager(layoutManager);
                        recyclerViewView.setItemAnimator(new DefaultItemAnimator());
                    } catch (ClassException| GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_back:
                Intent intent = getIntent().setClass(this, ActivityAddActivityF.class);
                startActivity(intent);
                this.finish();
                break;

        }
    }
    public void init() {
        textViewDate = (TextView) findViewById(R.id.result_dater);
        textViewName = (TextView) findViewById(R.id.result_namer);
        textViewTotal = (TextView) findViewById(R.id.result_totalr);
        recyclerViewView = (RecyclerView) findViewById(R.id.result_recyclerr);
        btnBack = (Button) findViewById(R.id.result_backr);
        btnBack.setOnClickListener(this);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            Log.i(TAG, "onCreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_z_result_activity);

            classId = getIntent().getExtras().getLong("classId");
            activityId = getIntent().getExtras().getLong("activityId");
            termId = getIntent().getExtras().getLong("termId");
            activity = activityService.getActivityById(activityId);

            init();
            new ActivityViewThread().start();
        }catch (GradingFactorException e) {
            e.printStackTrace();
        }
    }
}
