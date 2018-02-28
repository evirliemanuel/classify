package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.AssignmentResultAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.AssignmentResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AssignmentResultActivity extends AppCompatActivity {

    private static final String TAG = AssignmentResultActivity.class.getSimpleName();

    private final AssignmentService assignmentService = new AssignmentServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<AssignmentResult> resultList = new ArrayList<>();
    private Assignment assignment;

    private TextView textViewDate;
    private TextView textViewName;
    private TextView textViewTotal;
    private RecyclerView recyclerViewView;

    private long classId;
    private long termId;
    private long assignmentId;

    private class AssignmentViewThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String date = assignment.getDate();
                        String title = assignment.getTitle();
                        String itemTotal = String.valueOf(assignment.getItemTotal());

                        textViewDate.setText(date);
                        textViewName.setText(title);
                        textViewTotal.setText(itemTotal);

                        for (Student s : classService.getStudentList(classId)) {
                            AssignmentResult result = assignmentService.getAssignmentResultByAssignmentAndStudentId(assignmentId, s.getId());
                            if(result != null)
                                resultList.add(result);
                        }
                        AssignmentResultAdapter simpleAssignmentAdapter = new AssignmentResultAdapter(AssignmentResultActivity.this, resultList, assignment.getItemTotal());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AssignmentResultActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerViewView.setAdapter(simpleAssignmentAdapter);
                        recyclerViewView.setLayoutManager(layoutManager);
                        recyclerViewView.setItemAnimator(new DefaultItemAnimator());
                    } catch (ClassException| GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void init() {
        textViewDate = (TextView) findViewById(R.id.result_dater);
        textViewName = (TextView) findViewById(R.id.result_namer);
        textViewTotal = (TextView) findViewById(R.id.result_totalr);
        recyclerViewView = (RecyclerView) findViewById(R.id.result_recyclerr);
//        btnBack = (Button) findViewById(R.id.result_backr);
//        btnBack.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            Log.i(TAG, "onCreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_z_result_activity);

            classId = getIntent().getExtras().getLong("classId");
            assignmentId = getIntent().getExtras().getLong("assignmentId");
            termId = getIntent().getExtras().getLong("termId");
            assignment = assignmentService.getAssignmentById(assignmentId);

            init();
            new AssignmentViewThread().start();
        }catch (GradingFactorException e) {
            e.printStackTrace();
        }
    }
}
