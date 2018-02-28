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

import com.lieverandiver.thesisproject.adapter.ScheduleAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScheduleViewActivity extends AppCompatActivity {

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
        setContentView(R.layout.teacher_schedule_view);

        init();
        initRView(getIntent().getExtras().getLong("classId"));
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.teacher_schedule_recyclerview);
        btnSearch = (ToggleButton) findViewById(R.id._btn_search_schedule);
        btnSearchOk = (Button) findViewById(R.id._btn_search_ok_schedule);
        btnBack = (Button) findViewById(R.id.btn_back_schedule);
        editTextSearch = (EditText) findViewById(R.id.etxt_search_schedule);
        frameLayoutSearch = (FrameLayout) findViewById(R.id.frame_search_schedule);
        txtMsgContent = (TextView) findViewById(R.id.message_label_sc1);

        frameLayoutSearch.setVisibility(View.GONE);
        txtMsgContent.setVisibility(View.INVISIBLE);

        btnSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
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
                    final ClassServiceImpl classService = new ClassServiceImpl();
                    final List<Schedule> scheduleList = new ArrayList<>();
                    final Set<Schedule> scheduleSet = classService.getScheduleList(classId);

                    for (Schedule schedule : scheduleSet)
                        scheduleList.add(schedule);

                    final ScheduleAdapter scheduleAdapter = new ScheduleAdapter(
                            ScheduleViewActivity.this, scheduleList);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(
                            ScheduleViewActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(scheduleAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            if(scheduleSet.size() < 1)
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
