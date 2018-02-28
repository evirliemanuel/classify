package com.lieverandiver.thesisproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lieverandiver.thesisproject.adapter.GradeAdapter2;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.AttendanceService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.FormulaService;
import com.remswork.project.alice.service.GradeService;
import com.remswork.project.alice.service.ProjectService;
import com.remswork.project.alice.service.QuizService;
import com.remswork.project.alice.service.StudentService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.GradeServiceImpl;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import com.remswork.project.alice.service.impl.QuizServiceImpl;
import com.remswork.project.alice.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class GradeResultActivity2 extends AppCompatActivity {

    final ActivityService activityService = new ActivityServiceImpl();
    final AssignmentService assignmentService = new AssignmentServiceImpl();
    final AttendanceService attendanceService = new AttendanceServiceImpl();
    final ExamService examService = new ExamServiceImpl();
    final ProjectService projectService = new ProjectServiceImpl();
    final QuizService quizService = new QuizServiceImpl();

    final ClassService classService = new ClassServiceImpl();
    final FormulaService formulaService = new FormulaServiceImpl();
    final StudentService studentService = new StudentServiceImpl();

    private double activityGrade;
    private double assignmentGrade;
    private double attendanceGrade;
    private double examGrade;
    private double projectGrade;
    private double quizGrade;

    private boolean gradeIsReadyAct;
    private boolean gradeIsReadyAss;
    private boolean gradeIsReadyAtt;
    private boolean gradeIsReadyExa;
    private boolean gradeIsReadyPro;
    private boolean gradeIsReadyQui;

    private RecyclerView rvData;
    private GradeAdapter2 gradeAdapter;
    final List<Grade> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_activity_grade);
    }

    @Override
    protected void onResume() {
        super.onResume();

        rvData = (RecyclerView) findViewById(R.id.custom_recyclerview);
        gradeAdapter = new GradeAdapter2(getApplicationContext(), gradeList, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        rvData.setAdapter(gradeAdapter);
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());

        load();
    }

    public void load() {
        try {
            final FormulaService formulaService = new FormulaServiceImpl();
            final GradeService gradeService = new GradeServiceImpl();

            final long termId = getIntent().getExtras().getLong("termId");
            final long classId = getIntent().getExtras().getLong("classId");

            final Class _class = classService.getClassById(classId);

            final long subjectId = _class.getSubject() != null ? _class.getSubject().getId() : 0;
            final long teacherId = _class.getTeacher() != null ? _class.getTeacher().getId() : 0;

            final Formula formula = formulaService.getFormulaBySubjectAndTeacherId(subjectId, teacherId, termId);

            Log.i("SOMETHINGGG", "CLASSID" + classId + " FORMULAID" + formula.getId());
            for (final Student student : classService.getStudentList(classId)) {
                final Student cStudent = student;
                Log.i("SOMETHINGGG", "Student" + student.getId());
                Log.i("FORMULA",formula.getId()+ "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long studentId = cStudent.getId();
                            List<Grade> temp = gradeService.getGradeListByClass(classId, studentId, termId);
                            Grade grade = temp.size() > 0 ? temp.get(0) : new Grade();

                            Log.i("GRADE",grade.getId()+ "");

                            double actScore = ((double) formula.getActivityPercentage() / 100) * grade.getActivityScore();
                            double assScore = ((double) formula.getAssignmentPercentage() / 100) * grade.getAssignmentScore();
                            double attScore = ((double) formula.getAttendancePercentage() / 100) * grade.getAttendanceScore();
                            double exaScore = ((double) formula.getExamPercentage() / 100) * grade.getExamScore();
                            double proScore = ((double) formula.getProjectPercentage() / 100) * grade.getProjectScore();
                            double quiScore = ((double) formula.getQuizPercentage() / 100) * grade.getQuizScore();

                            grade.setTotalScore(
                                    actScore + assScore + attScore + exaScore + proScore + quiScore
                            );

                            grade.setStudent(student);

                            Log.i("SOMETHINGGG", "actScore" + formula.getActivityPercentage());
                            Log.i("SOMETHINGGG", "assScore" + formula.getAssignmentPercentage());
                            Log.i("SOMETHINGGG", "attScore" + formula.getAttendancePercentage());
                            Log.i("SOMETHINGGG", "exaScore" + formula.getExamPercentage());
                            Log.i("SOMETHINGGG", "proScore" + formula.getProjectPercentage());
                            Log.i("SOMETHINGGG", "quiScore" + formula.getQuizPercentage());
                            Log.i("SOMETHINGGG", "Student" + studentId);
                            Log.i("SOMETHINGGG", "Grade" + grade.getId());

                            gradeList.add(grade);
                            notifyChange();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void notifyChange() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gradeAdapter.notifyItemInserted(gradeList.size());
            }
        });
    }
}
