package com.lieverandiver.thesisproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lieverandiver.thesisproject.adapter.GradeAdapter2;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.AttendanceService;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.FormulaService;
import com.remswork.project.alice.service.ProjectService;
import com.remswork.project.alice.service.QuizService;
import com.remswork.project.alice.service.StudentService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import com.remswork.project.alice.service.impl.QuizServiceImpl;
import com.remswork.project.alice.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class GradeResultActivity extends AppCompatActivity{

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

    private RecyclerView recyclerView;
    private GradeAdapter2 gradeAdapter;
    final List<Grade> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_result);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = (RecyclerView) findViewById(R.id.grade_recycler_part);
        gradeAdapter = new GradeAdapter2(getApplicationContext(), gradeList, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setAdapter(gradeAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadBigData();
    }

    public void loadBigData() {
        //final long studentId = 462;
        final long classId = 1270;

        try {
            for(Student student : classService.getStudentList(classId)) {

                final long studentId = student.getId();
                Log.i("STUDENTIDdddddd", studentId + "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Activity> activityList = activityService.getActivityListByClassId(classId);
                                    final double fActivity[] = new double[activityList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fActivity.length; i++) {
                                        final double total = activityList.get(i).getItemTotal();
                                        final double score = activityService
                                                .getActivityResultByActivityAndStudentId(activityList.get(i).getId(), studentId).getScore();
                                        fActivity[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fActivity.length; i++)
                                        tempTotal += fActivity[i];
                                    //after looping
                                    tempTotal /= fActivity.length;
                                    activityGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyAct = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Assignment> assignmentList = assignmentService.getAssignmentListByClassId(classId);
                                    final double fAssignment[] = new double[assignmentList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fAssignment.length; i++) {
                                        final double total = assignmentList.get(i).getItemTotal();
                                        final double score = assignmentService
                                                .getAssignmentResultByAssignmentAndStudentId(assignmentList.get(i).getId(), studentId).getScore();
                                        fAssignment[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fAssignment.length; i++)
                                        tempTotal += fAssignment[i];
                                    //after looping
                                    tempTotal /= fAssignment.length;
                                    assignmentGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyAss = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Attendance> attendanceList = attendanceService.getAttendanceListByClassId(classId);
                                    final double fAttendance[] = new double[attendanceList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fAttendance.length; i++) {
                                        final double total = 1;
                                        final double score = attendanceService
                                                .getAttendanceResultByAttendanceAndStudentId(
                                                        attendanceList.get(i).getId(), studentId).getStatus() == 1 ? 1 : 0;
                                        fAttendance[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fAttendance.length; i++)
                                        tempTotal += fAttendance[i];
                                    //after looping
                                    tempTotal /= fAttendance.length;
                                    attendanceGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyAtt = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Exam> examList = examService.getExamListByClassId(classId);
                                    final double fExam[] = new double[examList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fExam.length; i++) {
                                        final double total = examList.get(i).getItemTotal();
                                        final double score = examService
                                                .getExamResultByExamAndStudentId(examList.get(i).getId(), studentId).getScore();
                                        fExam[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fExam.length; i++)
                                        tempTotal += fExam[i];
                                    //after looping
                                    tempTotal /= fExam.length;
                                    examGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyExa = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Project> projectList = projectService.getProjectListByClassId(classId);
                                    final double fProject[] = new double[projectList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fProject.length; i++) {
                                        final double total = projectList.get(i).getItemTotal();
                                        final double score = projectService
                                                .getProjectResultByProjectAndStudentId(projectList.get(i).getId(), studentId).getScore();
                                        fProject[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fProject.length; i++)
                                        tempTotal += fProject[i];
                                    //after looping
                                    tempTotal /= fProject.length;
                                    projectGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyPro = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final List<Quiz> quizList = quizService.getQuizListByClassId(classId);
                                    final double fQuiz[] = new double[quizList.size()];
                                    double tempTotal = 0;

                                    for (int i = 0; i < fQuiz.length; i++) {
                                        final double total = quizList.get(i).getItemTotal();
                                        final double score = quizService
                                                .getQuizResultByQuizAndStudentId(quizList.get(i).getId(), studentId).getScore();
                                        fQuiz[i] = (score / total) * 100;
                                    }
                                    for (int i = 0; i < fQuiz.length; i++)
                                        tempTotal += fQuiz[i];
                                    //after looping
                                    tempTotal /= fQuiz.length;
                                    quizGrade = tempTotal;

                                    //notify finish
                                    gradeIsReadyQui = true;
                                    if (isReady())
                                        notifyChange();

                                } catch (GradingFactorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }).start();

            }
        } catch (ClassException e) {
            e.printStackTrace();
        }

    }

    public boolean isReady() {
        if(!gradeIsReadyAct)
            return false;
        if(!gradeIsReadyAss)
            return false;
        if(!gradeIsReadyAtt)
            return false;
        if(!gradeIsReadyExa)
            return false;
        if(!gradeIsReadyPro)
            return false;
        if(!gradeIsReadyQui)
            return false;
        else
            return true;
    }

    public synchronized void notifyChange() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Grade grade = new Grade();
                grade.setTotalScore(activityGrade + assignmentGrade + attendanceGrade +
                        examGrade + projectGrade + quizGrade);
                gradeList.add(grade);
                gradeAdapter.notifyItemInserted(gradeList.size());
            }
        });
    }
}
