package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.CriteriaAdapter;
import com.lieverandiver.thesisproject.helper.FormulaHelper;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verlie on 9/1/2017.
 */

@Deprecated
public class Teacher_Activity_View_Subject_Datails  extends AppCompatActivity {

    private LinearLayout linearLayoutm;
    private LinearLayout linearLayoutf;

    private TextView name;
    private TextView code;
    private TextView desc;
    private TextView unit;
    Subject subject = new Subject();

    private TextView textViewMidtermPercent;
    private TextView textViewFinalsPercent;
    private RecyclerView recyclerViewMidterm;
    private RecyclerView recyclerViewFinals;

    private ToggleButton toggleButtonM;
    private ToggleButton toggleButtonF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_view_subject_datails);

        try {
            subject = new SubjectServiceImpl().getSubjectById(getIntent()
                    .getExtras().getLong("subjectId"));
        } catch (SubjectException e) {
            e.printStackTrace();
        }

        textViewMidtermPercent = (TextView) findViewById(R.id.midterm_percent_sub_view);
        textViewMidtermPercent = (TextView) findViewById(R.id.finals_percent_sub_view);
        recyclerViewMidterm = (RecyclerView) findViewById(R.id.midterm_recycleview);
        recyclerViewFinals = (RecyclerView) findViewById(R.id.finals_recycleview);

        linearLayoutm = (LinearLayout)findViewById(R.id.midterm_setting);
        linearLayoutf = (LinearLayout)findViewById(R.id.finals_setting);
        name = (TextView) findViewById(R.id.a_class_f_view_subject_name);
        code = (TextView) findViewById(R.id.a_class_f_view_subject_code);
        desc = (TextView) findViewById(R.id.a_class_f_view_subject_desc);
        unit = (TextView) findViewById(R.id.a_class_f_view_subject_unit);

        toggleButtonM =(ToggleButton)findViewById(R.id.toggle_midview);
        toggleButtonF =(ToggleButton)findViewById(R.id.toggle_finview);

        recyclerViewMidterm.setVisibility(View.GONE);
        recyclerViewFinals.setVisibility(View.GONE);

        toggleButtonM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                recyclerViewMidterm.setVisibility(View.VISIBLE);
            } else {
                recyclerViewMidterm.setVisibility(View.GONE);
            }
            }
        });

        toggleButtonF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerViewFinals.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewFinals.setVisibility(View.GONE);
                }
            }
        });

        name.setText(subject.getName());
        code.setText(subject.getCode());
        desc.setText(subject.getDescription());
        unit.setText(subject.getUnit() + "");

        try {
            Formula formula = new FormulaHelper(this).getFormula("1-midterm");
            if(formula != null && formula.getSubject() != null) {
                if(formula.getSubject().getId() == subject.getId()) {
                    recyclerViewMidterm.setVisibility(View.VISIBLE);
                    String key[] = new String[6];
                    key[0] = formula.getActivityPercentage() + "%";
                    key[1] = formula.getAssignmentPercentage() + "%";
                    key[2] = formula.getAttendancePercentage() + "%";
                    key[3] = formula.getExamPercentage() + "%";
                    key[4] = formula.getProjectPercentage() + "%";
                    key[5] = formula.getQuizPercentage() + "%";

                    List<String> datas = new ArrayList<>();
                    for (String s : key)
                        datas.add(s);

                    CriteriaAdapter criteriaAdapter = new CriteriaAdapter(this, datas);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewMidterm.setAdapter(criteriaAdapter);
                    recyclerViewMidterm.setLayoutManager(layoutManager);
                    recyclerViewMidterm.setItemAnimator(new DefaultItemAnimator());
                }
            }
        } catch (GradingFactorException e) {
            e.printStackTrace();
        }

        linearLayoutm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Teacher_Activity_View_Subject_Datails.this,
                        Teacher_GradingFactor_Activity_Midterm.class);
                intent.putExtra("subjectId", subject.getId());
                startActivity(intent);
                finish();
            }
        });

        linearLayoutf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Teacher_Activity_View_Subject_Datails.this,
                        Teacher_GradingFactor_Activity_Finals.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
