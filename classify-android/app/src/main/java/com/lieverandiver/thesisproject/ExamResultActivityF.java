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

import com.lieverandiver.thesisproject.adapter.ExamResultAdapter;
import com.lieverandiver.thesisproject.adapter.ExamResultAdapterF;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.ExamResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ExamResultActivityF extends AppCompatActivity {

    private static final String TAG = ExamResultActivityF.class.getSimpleName();

    private final ExamService examService = new ExamServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<ExamResult> resultList = new ArrayList<>();
    private Exam exam;

    private TextView textViewDate;
    private TextView textViewName;
    private TextView textViewTotal;
    private RecyclerView recyclerViewView;

    private long classId;
    private long termId;
    private long examId;

    private class ExamViewThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String date = exam.getDate();
                        String title = exam.getTitle();
                        String itemTotal = String.valueOf(exam.getItemTotal());

                        textViewDate.setText(date);
                        textViewName.setText(title);
                        textViewTotal.setText(itemTotal);

                        for (Student s : classService.getStudentList(classId)) {
                            ExamResult result = examService.getExamResultByExamAndStudentId(exam.getId(), s.getId());
                            if(result != null)
                                resultList.add(result);
                        }

                        ExamResultAdapterF simpleExamAdapterF = new ExamResultAdapterF(ExamResultActivityF.this, resultList, exam.getItemTotal());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ExamResultActivityF.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerViewView.setAdapter(simpleExamAdapterF);
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
            examId = getIntent().getExtras().getLong("examId");
            termId = getIntent().getExtras().getLong("termId");
            exam = examService.getExamById(examId);

            init();
            new ExamViewThread().start();
        }catch (GradingFactorException e) {
            e.printStackTrace();
        }
    }
}
