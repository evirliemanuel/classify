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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lieverandiver.thesisproject.adapter.ActivityAdapter;
import com.lieverandiver.thesisproject.adapter.ActivityAdapterF;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;

import java.util.List;

import static com.lieverandiver.thesisproject.R.id.add_add1;
import static com.lieverandiver.thesisproject.R.id.add_back1;
import static com.lieverandiver.thesisproject.R.id.add_grade1;

public class ActivityAddActivityF extends AppCompatActivity implements ActivityAdapterF.OnClickListener,
        View.OnClickListener {

    private static final String TAG = ActivityAddActivityF.class.getSimpleName();

    final ActivityService activityService = new ActivityServiceImpl();
    private ImageView imageView;
    private Button btnBackButton;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivity;
    private long classId;
    private long termId;
    private int size;
    private LinearLayout linearLayoutGrades;

    private class ActivityAddThread extends Thread {
        @Override
        public void run() {
            try {
                final List<Activity> activityList = activityService.getActivityListByClassId(classId, termId);
                size = activityList.size();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                    ActivityAdapterF activityAdapterF = new ActivityAdapterF(ActivityAddActivityF.this, activityList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityAddActivityF.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setAdapter(activityAdapterF);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    }
                });
            }catch (GradingFactorException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        linearLayoutActivity = (LinearLayout) findViewById(R.id.add_add1);
        recyclerView = (RecyclerView) findViewById(R.id.add_recycler1);
        btnBackButton = (Button) findViewById(R.id.add_back1);
        linearLayoutGrades = (LinearLayout)findViewById(R.id.add_grade1);
        linearLayoutGrades.setOnClickListener(this);

        linearLayoutActivity.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_add_activity);
        try {
            classId = getIntent().getExtras().getLong("classId");
            termId = getIntent().getExtras().getLong("termId");

            init();
            new ActivityAddThread().start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Activity activity, long activityId) {
        Intent intent = getIntent();
        intent.putExtra("activityId", activityId);
        intent.setClass(this, ActivityResultActivityF.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case add_add1 :
                Intent intent = getIntent().setClass(this, ActivityInputActivityF.class);
                startActivity(intent);
                this.finish();
                break;
            case add_back1 :
                intent = getIntent().setClass(this, ClassViewActivity.class);
                startActivity(intent);
                this.finish();
                break;

            case add_grade1 :
                intent = getIntent().setClass(this, ActivityGradeActivity.class);
                startActivity(intent);
                this.finish();
                break;

        }
        }
    }

