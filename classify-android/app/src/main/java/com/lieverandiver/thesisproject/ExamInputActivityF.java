package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.ExamInputAdapterF;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.GradeService;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import com.remswork.project.alice.service.impl.GradeServiceImpl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.lieverandiver.thesisproject.R.id.input_back4;
import static com.lieverandiver.thesisproject.R.id.input_ok4;
import static com.lieverandiver.thesisproject.R.id.input_tryagain4;
import static com.lieverandiver.thesisproject.R.id.input_tryagainemp4;

public class ExamInputActivityF extends AppCompatActivity implements View.OnClickListener {

    private final ClassService classService = new ClassServiceImpl();
    private final ExamService examService = new ExamServiceImpl();
    private final GradeService gradeService = new GradeServiceImpl();


    List<Student> studentList = new ArrayList<>();
    private EditText editTextName;
    private TextView textViewDate;
    private ToggleButton buttonSubmit;
    private RecyclerView recyclerViewStudentInput;
    private EditText editTextTotal;
    private Button btnBack;
    private CardView dialogSucces;
    private CardView dialogFailed;
    private Button btnTryAgain;
    private Button btnOk;
    private ExamInputAdapterF studentAdapter;
    private CardView getDialogEmptyTotal;
    private Button getBtnTryAgainEmptyTotal;
    private ToggleButton toggleButtonhideandshow;
    private FrameLayout frameLayouthideandshow;
    private Grade grade;
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_input_exam);
        init();

        buttonSubmit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long classId = getIntent().getExtras().getLong("classId");

                try {
                    Exam exam = new Exam();
                    exam.setTitle(!editTextName.getText().toString().trim().isEmpty() ?
                            editTextName.getText().toString().trim() : "Exam");
                    exam.setDate(textViewDate.getText().toString());

                    if (editTextTotal.getText().toString().equals("")) {
                        toggleButtonhideandshow.setChecked(false);
                        getDialogEmptyTotal.setVisibility(View.VISIBLE);
                        recyclerViewStudentInput.setVisibility(View.GONE);
                        return;
                    } else {
                        exam.setItemTotal(Integer.parseInt(editTextTotal.getText().toString()));
                    }

                    studentAdapter.setTotalItem(exam.getItemTotal());
                    studentAdapter.onValidate(true);

                    if (studentAdapter.isNoError()) {
                        exam = examService.addExam(exam, classId, 1L);
                        for (int i = 0; i < studentList.size(); i++) {
                            //Student id
                            final long studentId = studentList.get(i).getId();
                            //
                            int score = studentAdapter.getScore(i);
                            Student student = studentList.get(i);
                            examService.addExamResult(score, exam.getId(), student.getId());

                            //Adding Grade for exam
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        final List<Exam> examList = examService.getExamListByClassId(classId);
                                        final double fExam[] = new double[examList.size()];
                                        final long sId = studentId;
                                        double tempTotal = 0;

                                        try {
                                            List<Grade> tempList = gradeService.getGradeListByClass(classId, sId, 2L);
                                            grade = (tempList.size() > 0 ? tempList.get(0) : null);
                                        } catch (GradingFactorException e) {
                                            e.printStackTrace();
                                            grade = null;
                                        }
                                        if (grade == null) {
                                            Grade _grade = new Grade();
                                            grade = gradeService.addGrade(_grade, classId, studentId, 2L);
                                        }

                                        final Grade lGrade = grade;
                                        final long gradeId = grade.getId();

                                        for (int i = 0; i < fExam.length; i++) {
                                            final double total = examList.get(i).getItemTotal();
                                            final double score = examService
                                                    .getExamResultByExamAndStudentId(
                                                            examList.get(i).getId(), sId).getScore();
                                            fExam[i] = (score / total) * 100;
                                        }
                                        for (int i = 0; i < fExam.length; i++)
                                            tempTotal += fExam[i];

                                        //after looping
                                        if(fExam.length > 0)
                                            tempTotal /= fExam.length;
                                        else
                                            tempTotal = 0;

                                        lGrade.setActivityScore(tempTotal);
                                        lGrade.setTotalScore(lGrade.getTotalScore() + tempTotal);
                                        gradeService.updateGradeById(gradeId, lGrade);
                                    } catch (GradingFactorException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                        dialogSucces.setVisibility(View.VISIBLE);
                        Toast.makeText(ExamInputActivityF.this, "Success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ExamInputActivityF.this, "Failed", Toast.LENGTH_LONG).show();
                        dialogFailed.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case input_back4:
                Intent intent = getIntent().setClass(this, ExamAddActivityF.class);
                startActivity(intent);
                break;
            case input_ok4:
               intent = getIntent().setClass(this, ExamAddActivityF.class);
                startActivity(intent);
                break;

            case input_tryagainemp4:

                buttonSubmit.setChecked(false);
                buttonSubmit.setVisibility(View.VISIBLE);
                getDialogEmptyTotal.setVisibility(View.GONE);
                recyclerViewStudentInput.setVisibility(View.VISIBLE);
                break;

            case input_tryagain4:
                buttonSubmit.setChecked(false);
                buttonSubmit.setVisibility(View.VISIBLE);
                dialogFailed.setVisibility(View.GONE);
                break;

        }
    }

    public void init(){

        try {
            editTextName = (EditText) findViewById(R.id.input_name4);
            editTextTotal =(EditText) findViewById(R.id.input_total4);
            textViewDate = (TextView) findViewById(R.id.input_date4);
            buttonSubmit = (ToggleButton) findViewById(R.id.input_submit4);
            btnBack = (Button) findViewById(input_back4);
            dialogFailed = (CardView)findViewById(R.id.input_failed4);
            dialogSucces = (CardView)findViewById(R.id.input_succes4);
            btnOk = (Button) findViewById(input_ok4);
            btnTryAgain = (Button) findViewById(input_tryagain4);
            getDialogEmptyTotal = (CardView) findViewById(R.id.input_failedemp4);
            getBtnTryAgainEmptyTotal =(Button) findViewById(input_tryagainemp4);

            toggleButtonhideandshow = (ToggleButton) findViewById(R.id.input_hideandshow4);
            frameLayouthideandshow = (FrameLayout)findViewById(R.id.input_detailts4);

            toggleButtonhideandshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        frameLayouthideandshow.setVisibility(View.GONE);
                    }else{
                        frameLayouthideandshow.setVisibility(View.VISIBLE);
                    }
                }
            });

            getDialogEmptyTotal.setVisibility(View.GONE);
            dialogSucces.setVisibility(View.GONE);
            dialogFailed.setVisibility(View.GONE);

            btnOk.setOnClickListener(this);
            btnBack.setOnClickListener(this);

            buttonSubmit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonSubmit.setVisibility(View.GONE);

                    }
                }
            });

            getBtnTryAgainEmptyTotal.setOnClickListener(this);
            btnTryAgain.setOnClickListener(this);

            recyclerViewStudentInput = (RecyclerView) findViewById(R.id.input_recyclerview4);
            recyclerViewStudentInput.setVisibility(View.VISIBLE);

            for(Student s : classService.getStudentList(getIntent().getExtras().getLong("classId")))
                studentList.add(s);

            studentAdapter = new ExamInputAdapterF(this, studentList);
            recyclerViewStudentInput.setAdapter(studentAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerViewStudentInput.setLayoutManager(layoutManager);
            recyclerViewStudentInput.setItemAnimator(new DefaultItemAnimator());

            String date = String.format(Locale.ENGLISH, "%02d/%02d/%d" , Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+ 1,
                    Calendar.getInstance().get(Calendar.YEAR));
            textViewDate.setText(date);


        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface InputListener {
        void onValidate(boolean doValidate);
        boolean isNoError();
        int getScore(int index);
        void setTotalItem(int score);
    }

}
