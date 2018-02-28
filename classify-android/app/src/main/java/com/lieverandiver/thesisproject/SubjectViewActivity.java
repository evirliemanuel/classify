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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.CriteriaAdapter2;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.FormulaService;
import com.remswork.project.alice.service.SubjectService;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;

import static com.lieverandiver.thesisproject.R.id.finals_setting;
import static com.lieverandiver.thesisproject.R.id.midterm_setting;
import static com.lieverandiver.thesisproject.R.id.toggle_finview;
import static com.lieverandiver.thesisproject.R.id.toggle_midview;

public class SubjectViewActivity  extends AppCompatActivity implements CompoundButton
        .OnCheckedChangeListener, View.OnClickListener {

    private final SubjectService subjectService = new SubjectServiceImpl();
    private final FormulaService formulaService = new FormulaServiceImpl();
    private Intent intent;
    private Subject subject;
    private Teacher teacher;
    private Formula formulaMidterm;
    private Formula formulaFinalterm;

    private TextView name;
    private TextView code;
    private TextView desc;
    private TextView unit;

    private LinearLayout linearLayoutm;
    private LinearLayout linearLayoutf;

    private TextView textViewMidtermPercent;
    private TextView textViewFinalsPercent;
    private RecyclerView recyclerViewMidterm;
    private RecyclerView recyclerViewFinals;


    private ToggleButton toggleButtonM;
    private ToggleButton toggleButtonF;

    private Button imageViewBackButton;

    private void init() {
        imageViewBackButton = (Button)findViewById(R.id.add_backsubject);
        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();

            }
        });

        linearLayoutm = (LinearLayout)findViewById(R.id.midterm_setting);
        linearLayoutf = (LinearLayout)findViewById(R.id.finals_setting);

        name = (TextView) findViewById(R.id.a_class_f_view_subject_name);
        code = (TextView) findViewById(R.id.a_class_f_view_subject_code);
        desc = (TextView) findViewById(R.id.a_class_f_view_subject_desc);
        unit = (TextView) findViewById(R.id.a_class_f_view_subject_unit);

        toggleButtonM = (ToggleButton)findViewById(toggle_midview);
        toggleButtonF = (ToggleButton)findViewById(toggle_finview);

        textViewMidtermPercent = (TextView) findViewById(R.id.midterm_percent_sub_view);
        textViewFinalsPercent = (TextView) findViewById(R.id.finals_percent_sub_view);
        recyclerViewMidterm = (RecyclerView) findViewById(R.id.midterm_recycleview);

        recyclerViewFinals = (RecyclerView) findViewById(R.id.finals_recycleview);

        recyclerViewFinals.setVisibility(View.GONE);
        recyclerViewMidterm.setVisibility(View.GONE);

        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    recyclerViewMidterm.setVisibility(View.GONE);
                    recyclerViewFinals.setVisibility(View.GONE);

                    toggleButtonM.setOnCheckedChangeListener(SubjectViewActivity.this);
                    toggleButtonF.setOnCheckedChangeListener(SubjectViewActivity.this);

                    linearLayoutm.setOnClickListener(SubjectViewActivity.this);
                    linearLayoutf.setOnClickListener(SubjectViewActivity.this);

                    name.setText(subject.getName());
                    code.setText(subject.getCode());
                    desc.setText(subject.getDescription());
                    unit.setText(String.valueOf(subject.getUnit()));

                    Formula formula = formulaService.getFormulaBySubjectAndTeacherId(
                            subject.getId(), teacher.getId(), 1);
                    if(formula != null && formula.getSubject() != null) {
                        if(formula.getSubject().getId() == subject.getId()) {
                            formulaMidterm = formula;

                            String percents[] = new String[6];
                            percents[0] = formula.getActivityPercentage() + "%";
                            percents[1] = formula.getAssignmentPercentage() + "%";
                            percents[2] = formula.getAttendancePercentage() + "%";
                            percents[3] = formula.getExamPercentage() + "%";
                            percents[4] = formula.getProjectPercentage() + "%";
                            percents[5] = formula.getQuizPercentage() + "%";

                            CriteriaAdapter2 criteriaAdapter =
                                    new CriteriaAdapter2(SubjectViewActivity.this, percents);
                            LinearLayoutManager layoutManager =
                                    new LinearLayoutManager(SubjectViewActivity.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerViewMidterm.setAdapter(criteriaAdapter);
                            recyclerViewMidterm.setLayoutManager(layoutManager);
                            recyclerViewMidterm.setItemAnimator(new DefaultItemAnimator());
                        }
                    }

                    formula = formulaService.getFormulaBySubjectAndTeacherId(
                            subject.getId(), teacher.getId(), 2);
                    if(formula != null && formula.getSubject() != null) {
                        if(formula.getSubject().getId() == subject.getId()) {
                            formulaFinalterm = formula;

                            String percents[] = new String[6];
                            percents[0] = formula.getActivityPercentage() + "%";
                            percents[1] = formula.getAssignmentPercentage() + "%";
                            percents[2] = formula.getAttendancePercentage() + "%";
                            percents[3] = formula.getExamPercentage() + "%";
                            percents[4] = formula.getProjectPercentage() + "%";
                            percents[5] = formula.getQuizPercentage() + "%";

                            CriteriaAdapter2 criteriaAdapter =
                                    new CriteriaAdapter2(SubjectViewActivity.this, percents);
                            LinearLayoutManager layoutManager =
                                    new LinearLayoutManager(SubjectViewActivity.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerViewFinals.setAdapter(criteriaAdapter);
                            recyclerViewFinals.setLayoutManager(layoutManager);
                            recyclerViewFinals.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                } catch (GradingFactorException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_view_subject_datails);

        try {
            intent = getIntent();
            long subjectId = intent.getExtras().getLong("subjectId");
            subject = subjectService.getSubjectById(subjectId);
            teacher = new TeacherHelper(this).loadUser().get();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            }).start();
        } catch (SubjectException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()) {
            case toggle_midview :
                if(isChecked)
                    recyclerViewMidterm.setVisibility(View.VISIBLE);
                else
                    recyclerViewMidterm.setVisibility(View.GONE);
                break;
            case toggle_finview :
                if (isChecked)
                    recyclerViewFinals.setVisibility(View.VISIBLE);
                else
                    recyclerViewFinals.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case midterm_setting :
                if(formulaMidterm != null) {
                    intent.putExtra("isExist", true);
                    intent.putExtra("formulaId", formulaMidterm.getId());
                }else
                    intent.putExtra("isExist", false);
                intent.setClass(this, CriteriaMidtermInputActivity.class);
                startActivity(intent);
                finish();
                break;
            case finals_setting :
                if(formulaFinalterm != null) {
                    intent.putExtra("isExist", true);
                    intent.putExtra("formulaId", formulaFinalterm.getId());
                }else
                    intent.putExtra("isExist", false);
                intent.setClass(this, CriteriaFinaltermInputActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
