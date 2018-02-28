package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.AttendanceService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.FormulaService;
import com.remswork.project.alice.service.ProjectService;
import com.remswork.project.alice.service.QuizService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import com.remswork.project.alice.service.impl.QuizServiceImpl;

import static com.lieverandiver.thesisproject.R.id.class_backbutton;
import static com.lieverandiver.thesisproject.R.id.ftogglebutton;
import static com.lieverandiver.thesisproject.R.id.mtogglebutton;
import static com.lieverandiver.thesisproject.R.id.view_grade;
import static com.lieverandiver.thesisproject.R.id.view_schedule;
import static com.lieverandiver.thesisproject.R.id.view_student;
import static com.lieverandiver.thesisproject.R.id.viewactivityf;
import static com.lieverandiver.thesisproject.R.id.viewactivitym;
import static com.lieverandiver.thesisproject.R.id.viewassignmentf;
import static com.lieverandiver.thesisproject.R.id.viewassignmentm;
import static com.lieverandiver.thesisproject.R.id.viewattendancef;
import static com.lieverandiver.thesisproject.R.id.viewattendancem;
import static com.lieverandiver.thesisproject.R.id.viewexamf;
import static com.lieverandiver.thesisproject.R.id.viewexamm;
import static com.lieverandiver.thesisproject.R.id.viewprojectf;
import static com.lieverandiver.thesisproject.R.id.viewprojectm;
import static com.lieverandiver.thesisproject.R.id.viewquizf;
import static com.lieverandiver.thesisproject.R.id.viewquizm;

public class ClassViewActivity extends AppCompatActivity implements View.OnClickListener ,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = ClassViewActivity.class.getSimpleName();
    private final ActivityService activityService = new ActivityServiceImpl();
    private final AttendanceService attendanceService = new AttendanceServiceImpl();
    private final AssignmentService assignmentService = new AssignmentServiceImpl();
    private final ExamService examService = new ExamServiceImpl();
    private final ProjectService projectService = new ProjectServiceImpl();
    private final QuizService quizService = new QuizServiceImpl();

    private final FormulaService formulaService = new FormulaServiceImpl();
    private final ClassService classService = new ClassServiceImpl();

    private TextView txtViewSubjectName;
    private TextView txtViewSectionName;
    private TextView txtViewDepName;
    private TextView txtFormat;
    private CardView viewStudent;
    private CardView viewSchedule;
    private CardView viewGrade;
    private long classId;

    private ToggleButton toggleButtonShowandHideM;
    private ToggleButton toggleButtonShowandHideF;
    private LinearLayout linearLayoutShowandHideM;
    private LinearLayout linearLayoutShowandHideF;

    private LinearLayout linearLayoutActivityM;
    private LinearLayout linearLayoutAssignmentM;
    private LinearLayout linearLayoutAttendanceM;
    private LinearLayout linearLayoutExamM;
    private LinearLayout linearLayoutProjectM;
    private LinearLayout linearLayoutQuizM;

    private LinearLayout linearLayoutActivityF;
    private LinearLayout linearLayoutAssignmentF;
    private LinearLayout linearLayoutAttendanceF;
    private LinearLayout linearLayoutExamF;
    private LinearLayout linearLayoutProjectF;
    private LinearLayout linearLayoutQuizF;

    private TextView textViewActivityM;
    private TextView textViewAssignmentM;
    private TextView textViewAttendanceM;
    private TextView textViewExamM;
    private TextView textViewProjectM;
    private TextView textViewQuizM;

    private TextView textViewActivityF;
    private TextView textViewAssignmentF;
    private TextView textViewAttendanceF;
    private TextView textViewExamF;
    private TextView textViewProjectF;
    private TextView textViewQuizF;

    private Button btnBack;

    private CardView cToggle;
    private CardView cToggle2;
    private FrameLayout scrnBlock;

    private CardView mcvActivity;
    private CardView mcvAssignment;
    private CardView mcvAttendance;
    private CardView mcvExam;
    private CardView mcvProject;
    private CardView mcvQuiz;

    private CardView fcvActivity;
    private CardView fcvAssignment;
    private CardView fcvAttendance;
    private CardView fcvExam;
    private CardView fcvProject;
    private CardView fcvQuiz;

    private Formula mFormula;
    private Formula fFormula;

    public void init() {

        textViewActivityM = (TextView)findViewById(R.id.totalm1);
        textViewAssignmentM = (TextView)findViewById(R.id.totalm2);
        textViewAttendanceM = (TextView)findViewById(R.id.totalm3);
        textViewExamM = (TextView)findViewById(R.id.totalm4);
        textViewProjectM = (TextView)findViewById(R.id.totalm5);
        textViewQuizM = (TextView)findViewById(R.id.totalm6);

        textViewActivityF = (TextView)findViewById(R.id.totalf1);
        textViewAssignmentF = (TextView)findViewById(R.id.totalf2);
        textViewAttendanceF = (TextView)findViewById(R.id.totalf3);
        textViewExamF = (TextView)findViewById(R.id.totalf4);
        textViewProjectF = (TextView)findViewById(R.id.totalf5);
        textViewQuizF = (TextView)findViewById(R.id.totalf6);

        viewSchedule = (CardView) findViewById(R.id.view_schedule);
        viewStudent = (CardView) findViewById(R.id.view_student);
        viewGrade = (CardView) findViewById(R.id.view_grade);
        txtViewSubjectName = (TextView) findViewById(R.id.txtv_subjectname);
        txtViewSectionName = (TextView) findViewById(R.id.txtv_section);
        txtViewDepName = (TextView) findViewById(R.id.txtv_dept);
        txtFormat = (TextView) findViewById(R.id.txtv_sem);
        btnBack = (Button)findViewById(R.id.class_backbutton);

        viewSchedule.setOnClickListener(this);
        viewStudent.setOnClickListener(this);
        viewGrade.setOnClickListener(this);

        toggleButtonShowandHideM = (ToggleButton)findViewById(mtogglebutton);
        toggleButtonShowandHideF = (ToggleButton)findViewById(ftogglebutton);
        linearLayoutShowandHideM = (LinearLayout)findViewById(R.id.mlinear_showandhide);
        linearLayoutShowandHideF = (LinearLayout)findViewById(R.id.flinear_showandhide);

        linearLayoutActivityM =(LinearLayout)findViewById(R.id.viewactivitym);
        linearLayoutAssignmentM =(LinearLayout)findViewById(R.id.viewassignmentm);
        linearLayoutAttendanceM =(LinearLayout)findViewById(R.id.viewattendancem);
        linearLayoutExamM =(LinearLayout)findViewById(R.id.viewexamm);
        linearLayoutProjectM =(LinearLayout)findViewById(R.id.viewprojectm);
        linearLayoutQuizM =(LinearLayout)findViewById(R.id.viewquizm);

        linearLayoutActivityF =(LinearLayout)findViewById(viewactivityf);
        linearLayoutAssignmentF =(LinearLayout)findViewById(viewassignmentf);
        linearLayoutAttendanceF =(LinearLayout)findViewById(viewattendancef);
        linearLayoutExamF =(LinearLayout)findViewById(viewexamf);
        linearLayoutProjectF =(LinearLayout)findViewById(viewprojectf);
        linearLayoutQuizF =(LinearLayout)findViewById(viewquizf);

        cToggle = (CardView) findViewById(R.id.card_toggle_part);
        cToggle2 = (CardView) findViewById(R.id.card_toggle_part2);

        mcvActivity = (CardView) findViewById(R.id.card_activity_formula_supportm);
        mcvAssignment = (CardView) findViewById(R.id.card_assignment_formula_supportm);
        mcvAttendance = (CardView) findViewById(R.id.card_attendance_formula_supportm);
        mcvExam = (CardView) findViewById(R.id.card_exam_formula_supportm);
        mcvProject = (CardView) findViewById(R.id.card_project_formula_supportm);
        mcvQuiz = (CardView) findViewById(R.id.card_quiz_formula_supportm);

        fcvActivity = (CardView) findViewById(R.id.card_activity_formula_supportf);
        fcvAssignment = (CardView) findViewById(R.id.card_assignment_formula_supportf);
        fcvAttendance = (CardView) findViewById(R.id.card_attendance_formula_supportf);
        fcvExam = (CardView) findViewById(R.id.card_exam_formula_supportf);
        fcvProject = (CardView) findViewById(R.id.card_project_formula_supportf);
        fcvQuiz = (CardView) findViewById(R.id.card_quiz_formula_supportf);

        scrnBlock = (FrameLayout) findViewById(R.id.screen_time_not);

        btnBack.setOnClickListener(this);
        cToggle.setOnClickListener(this);
        cToggle2.setOnClickListener(this);

        linearLayoutActivityM.setOnClickListener(this);
        linearLayoutAssignmentM.setOnClickListener(this);
        linearLayoutAttendanceM.setOnClickListener(this);
        linearLayoutExamM.setOnClickListener(this);
        linearLayoutProjectM.setOnClickListener(this);
        linearLayoutQuizM.setOnClickListener(this);

        linearLayoutActivityF.setOnClickListener(this);
        linearLayoutAssignmentF.setOnClickListener(this);
        linearLayoutAttendanceF.setOnClickListener(this);
        linearLayoutExamF.setOnClickListener(this);
        linearLayoutProjectF.setOnClickListener(this);
        linearLayoutQuizF.setOnClickListener(this);

        linearLayoutActivityM.setVisibility(View.VISIBLE);
        linearLayoutAssignmentM.setVisibility(View.VISIBLE);
        linearLayoutAttendanceM.setVisibility(View.VISIBLE);
        linearLayoutExamM.setVisibility(View.VISIBLE);
        linearLayoutProjectM.setVisibility(View.VISIBLE);
        linearLayoutQuizM.setVisibility(View.VISIBLE);

        linearLayoutActivityF.setVisibility(View.VISIBLE);
        linearLayoutAssignmentF.setVisibility(View.VISIBLE);
        linearLayoutAttendanceF.setVisibility(View.VISIBLE);
        linearLayoutExamF.setVisibility(View.VISIBLE);
        linearLayoutProjectF.setVisibility(View.VISIBLE);
        linearLayoutQuizF.setVisibility(View.VISIBLE);

        mcvActivity.setVisibility(View.GONE);
        mcvAssignment.setVisibility(View.GONE);
        mcvAttendance .setVisibility(View.GONE);
        mcvExam.setVisibility(View.GONE);
        mcvProject.setVisibility(View.GONE);
        mcvQuiz.setVisibility(View.GONE);

        fcvActivity.setVisibility(View.GONE);
        fcvAssignment.setVisibility(View.GONE);
        fcvAttendance.setVisibility(View.GONE);
        fcvExam.setVisibility(View.GONE);
        fcvProject.setVisibility(View.GONE);
        fcvQuiz.setVisibility(View.GONE);

        linearLayoutShowandHideF.setVisibility(View.GONE);
        linearLayoutShowandHideM.setVisibility(View.GONE);

        toggleButtonShowandHideM.setOnCheckedChangeListener(this);
        toggleButtonShowandHideF.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()) {
            case mtogglebutton :
            if(isChecked)
                linearLayoutShowandHideM.setVisibility(View.VISIBLE);
            else
              linearLayoutShowandHideM.setVisibility(View.GONE);
            break;
            case ftogglebutton :
                if(isChecked)
                    linearLayoutShowandHideF.setVisibility(View.VISIBLE);
                else
                    linearLayoutShowandHideF.setVisibility(View.GONE);
                break;
        }
    }

    public class ClassViewThread extends Thread {

        @Override
        public void run() {
            try {
                final int sizeAc = activityService.getActivityListByClassId(classId, 1L).size();
                final int sizeAtt = attendanceService.getAttendanceListByClassId(classId, 1L).size();
                final int sizeAss = assignmentService.getAssignmentListByClassId(classId, 1L).size();
                final int sizeExam = examService.getExamListByClassId(classId, 1L).size();
                final int sizePro = projectService.getProjectListByClassId(classId, 1L).size();
                final int sizeQuiz = quizService.getQuizListByClassId(classId, 1L).size();
                final int sizeAcF = activityService.getActivityListByClassId(classId, 2L).size();
                final int sizeAttF = attendanceService.getAttendanceListByClassId(classId, 2L).size();
                final int sizeAssF = assignmentService.getAssignmentListByClassId(classId, 2L).size();
                final int sizeExamF = examService.getExamListByClassId(classId, 2L).size();
                final int sizeProF = projectService.getProjectListByClassId(classId, 2L).size();
                final int sizeQuizF = quizService.getQuizListByClassId(classId, 2L).size();
                final Class _class = classService.getClassById(classId);

                try {
                    mFormula = formulaService.getFormulaBySubjectAndTeacherId(
                            _class.getSubject().getId(), _class.getTeacher().getId(), 1);
                }catch (GradingFactorException e) {
                    mFormula = null;
                }
                try {
                    fFormula = formulaService.getFormulaBySubjectAndTeacherId(
                            _class.getSubject().getId(), _class.getTeacher().getId(), 2);
                }catch (GradingFactorException e) {
                    fFormula = null;
                }
//                if(mFormula == null && _class.getSubject() != null)
//                    mFormula = formulaService.addFormula(new Formula(), _class.getSubject().getId(),
//                            _class.getTeacher().getId());
//                if(fFormula == null && _class.getSubject() != null)
//                    fFormula = formulaService.addFormula(new Formula(), _class.getSubject().getId(),
//                            _class.getTeacher().getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String subjectName = (_class.getSubject() != null ? _class.getSubject().getName() : "None");
                        String sectionName = (_class.getSection() != null ? _class.getSection().getName() : "None");
                        String departmentName = (_class.getSection() != null ? _class.getSection().getDepartment().getName() : "None");

                        if(mFormula != null) {

                            if(mFormula.getActivityPercentage() > 0)
                                mcvActivity.setVisibility(View.VISIBLE);
                            if(mFormula.getAssignmentPercentage() > 0)
                                mcvAssignment.setVisibility(View.VISIBLE);
                            if(mFormula.getAttendancePercentage() > 0)
                                mcvAttendance .setVisibility(View.VISIBLE);
                            if(mFormula.getExamPercentage() > 0)
                                mcvExam.setVisibility(View.VISIBLE);
                            if(mFormula.getProjectPercentage() > 0)
                                mcvProject.setVisibility(View.VISIBLE);
                            if(mFormula.getQuizPercentage() > 0)
                                mcvQuiz.setVisibility(View.VISIBLE);

                            String totalActivitySize = String.valueOf(sizeAc);
                            String totalAttendanceSize = String.valueOf(sizeAtt);
                            String totalAssignmentSize = String.valueOf(sizeAss);
                            String totalExamSize = String.valueOf(sizeExam);
                            String totalProjectSize = String.valueOf(sizePro);
                            String totalQuizSize = String.valueOf(sizeQuiz);

                            textViewActivityM.setText(totalActivitySize);
                            textViewAttendanceM.setText(totalAttendanceSize);
                            textViewAssignmentM.setText(totalAssignmentSize);
                            textViewExamM.setText(totalExamSize);
                            textViewProjectM.setText(totalProjectSize);
                            textViewQuizM.setText(totalQuizSize);

                        }else
                            viewGrade.setEnabled(false);

                        if(fFormula != null) {

                            if(fFormula.getActivityPercentage() > 0)
                                fcvActivity.setVisibility(View.VISIBLE);
                            if(fFormula.getAssignmentPercentage() > 0)
                                fcvAssignment.setVisibility(View.VISIBLE);
                            if(fFormula.getAttendancePercentage() > 0)
                                fcvAttendance.setVisibility(View.VISIBLE);
                            if(fFormula.getExamPercentage() > 0)
                                fcvExam.setVisibility(View.VISIBLE);
                            if(fFormula.getProjectPercentage() > 0)
                                fcvProject.setVisibility(View.VISIBLE);
                            if(fFormula.getQuizPercentage() > 0)
                                fcvQuiz.setVisibility(View.VISIBLE);

                            String totalActivitySizeF = String.valueOf(sizeAcF);
                            String totalAttendanceSizeF = String.valueOf(sizeAttF);
                            String totalAssignmentSizeF = String.valueOf(sizeAssF);
                            String totalExamSizeF = String.valueOf(sizeExamF);
                            String totalProjectSizeF = String.valueOf(sizeProF);
                            String totalQuizSizeF = String.valueOf(sizeQuizF);

                            textViewActivityF.setText(totalActivitySizeF);
                            textViewAttendanceF.setText(totalAttendanceSizeF);
                            textViewAssignmentF.setText(totalAssignmentSizeF);
                            textViewExamF.setText(totalExamSizeF);
                            textViewProjectF.setText(totalProjectSizeF);
                            textViewQuizF.setText(totalQuizSizeF);
                        }

                        txtViewSubjectName.setText(subjectName);
                        txtViewSectionName.setText(sectionName);
                        txtViewDepName.setText(departmentName);

                        scrnBlock.setVisibility(View.GONE);
                    }
                });
            } catch (GradingFactorException e) {
                e.printStackTrace();
            } catch (ClassException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activty_view_class);
        try {
            classId = getIntent().getExtras().getLong("classId");
            init();
            new ClassViewThread().start();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case view_schedule :
                Intent intent = getIntent().setClass(this, ScheduleViewActivity.class);
                startActivity(intent);
                break;
            case view_student :
                intent = getIntent().setClass(this, StudentViewActivity.class);
                startActivity(intent);
                break;
            case view_grade :
                intent = getIntent().setClass(this, GradeResultActivity2.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;
            case viewactivitym :
                intent = getIntent().setClass(this, ActivityAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;
            case viewassignmentm :
                intent = getIntent().setClass(this, AssignmentAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;
            case viewattendancem :
                intent = getIntent().setClass(this, AttendanceAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;
            case viewexamm :
                intent = getIntent().setClass(this, ExamAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;
            case viewprojectm :
                intent = getIntent().setClass(this, ProjectAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;

            case viewquizm :
                intent = getIntent().setClass(this, QuizAddActivity.class);
                intent.putExtra("termId", 1L);
                startActivity(intent);
                break;

            case viewactivityf :
                intent = getIntent().setClass(this, ActivityAddActivityF.class);
                intent.putExtra("termId", 2L);
                startActivity(intent);
                break;
            case viewassignmentf :
                intent = getIntent().setClass(this, AssignmentAddActivityF.class);
                intent.putExtra("termId", 2L);
                startActivity(intent);
                break;
            case viewattendancef :
                intent = getIntent().setClass(this, AttendanceAddActivityF.class);
                intent.putExtra("termId",2L);
                startActivity(intent);
                break;
            case viewexamf :
                intent = getIntent().setClass(this, ExamAddActivityF.class);
                intent.putExtra("termId", 2L);
                startActivity(intent);
                break;
            case viewprojectf :
                intent = getIntent().setClass(this, ProjectAddActivityF.class);
                intent.putExtra("termId", 2L);
                startActivity(intent);
                break;

            case viewquizf :
                intent = getIntent().setClass(this, QuizAddActivityF.class);
                intent.putExtra("termId", 2L);
                startActivity(intent);
                break;

            case class_backbutton :
                intent = getIntent().setClass(this, MainActivity2.class);
                startActivity(intent);
                break;
            case R.id.card_toggle_part :
                toggleButtonShowandHideM.setChecked(!toggleButtonShowandHideM.isChecked());
                break;
            case R.id.card_toggle_part2 :
                toggleButtonShowandHideF.setChecked(!toggleButtonShowandHideF.isChecked());
                break;
        }
    }
}
