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

import com.lieverandiver.thesisproject.adapter.QuizResultAdapter;
import com.lieverandiver.thesisproject.adapter.QuizResultAdapterF;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.QuizResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.QuizService;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.QuizServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class QuizResultActivityF extends AppCompatActivity {

    private static final String TAG = QuizResultActivityF.class.getSimpleName();
    private final QuizService quizService = new QuizServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<QuizResult> resultList = new ArrayList<>();
    private Quiz quiz;

    private TextView textViewDate;
    private TextView textViewName;
    private TextView textViewTotal;
    private RecyclerView recyclerViewView;

    private long classId;
    private long termId;
    private long quizId;

    private class ExamViewThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String date = quiz.getDate();
                        String title = quiz.getTitle();
                        String itemTotal = String.valueOf(quiz.getItemTotal());

                        textViewDate.setText(date);
                        textViewName.setText(title);
                        textViewTotal.setText(itemTotal);

                        for (Student s : classService.getStudentList(classId)) {
                            QuizResult result = quizService.getQuizResultByQuizAndStudentId
                                    (quiz.getId(), s.getId());
                            if(result != null)
                                resultList.add(result);
                        }

                       QuizResultAdapterF simpleQuizAdapterF = new QuizResultAdapterF
                               (QuizResultActivityF.this, resultList, quiz.getItemTotal());
                        LinearLayoutManager layoutManager = new LinearLayoutManager
                                (QuizResultActivityF.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerViewView.setAdapter(simpleQuizAdapterF);
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
            quizId = getIntent().getExtras().getLong("quizId");
            termId = getIntent().getExtras().getLong("termId");
           quiz = quizService.getQuizById(quizId);

            init();
            new ExamViewThread().start();
        }catch (GradingFactorException e) {
            e.printStackTrace();
        }
    }
}
