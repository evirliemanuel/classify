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

import com.lieverandiver.thesisproject.adapter.QuizAdapter;
import com.lieverandiver.thesisproject.adapter.QuizAdapterF;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.service.QuizService;
import com.remswork.project.alice.service.impl.QuizServiceImpl;

import java.util.List;

import static com.lieverandiver.thesisproject.R.id.add_add6;
import static com.lieverandiver.thesisproject.R.id.add_grade6;
import static com.lieverandiver.thesisproject.R.id.btn_backaddactivity;

public class QuizAddActivityF extends AppCompatActivity implements QuizAdapterF.OnClickListener,
        View.OnClickListener {

    private static final String TAG = QuizAddActivityF.class.getSimpleName();

    final QuizService quizService = new QuizServiceImpl();
    private ImageView imageView;
    private Button btnBackButton;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivity;
    private long classId;
    private long termId;
    private LinearLayout linearLayoutGrades;

    @Override
    public void onClick(Quiz quiz, long quizId) {
        Intent intent = getIntent();
        intent.putExtra("quizId", quizId);
        intent.setClass(this, QuizResultActivity.class);
        startActivity(intent);
    }

    private class ActivityAddThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Quiz> quizList = quizService.getQuizListByClassId(classId, termId);
                        QuizAdapterF quizAdapterF = new QuizAdapterF(QuizAddActivityF.this, quizList);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(QuizAddActivityF.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerView.setAdapter(quizAdapterF);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }catch (GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void init() {
        linearLayoutGrades = (LinearLayout)findViewById(R.id.add_grade6);
        linearLayoutActivity = (LinearLayout) findViewById(R.id.add_add6);
        recyclerView = (RecyclerView) findViewById(R.id.add_recycler6);
        btnBackButton = (Button) findViewById(R.id.add_back6);
        linearLayoutGrades.setOnClickListener(this);
        linearLayoutActivity.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_add_quiz);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case add_add6 :
                Intent intent = getIntent().setClass(this,QuizInputActivityF.class);
                startActivity(intent);
                break;
            case btn_backaddactivity :
                intent = getIntent().setClass(this,ClassViewActivity.class);
                startActivity(intent);
                break;

            case add_grade6 :
                intent = getIntent().setClass(this, ActivityGradeQuiz.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
