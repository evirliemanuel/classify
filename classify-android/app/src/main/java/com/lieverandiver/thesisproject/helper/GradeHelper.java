package com.lieverandiver.thesisproject.helper;

import android.util.Log;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.Formula;
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
import com.remswork.project.alice.service.impl.GradeServiceImpl;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import com.remswork.project.alice.service.impl.QuizServiceImpl;
import com.remswork.project.alice.service.impl.StudentServiceImpl;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

@Deprecated
public class GradeHelper {

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

    private Class _class;
    private Formula formula;
    private Student cStudent;

    private long classId;
    private long studentId;
    private long termId;

    private Listener listener;

    public GradeHelper() {

    }

    public List<Grade> getGradeList() {
        return null;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Grade computeGrade(final long classId, final long studentId, final long termId) {
        try {
            this.classId = classId;
            this.studentId = studentId;
            this.termId = termId;

            _class = classService.getClassById(classId);
            formula = formulaService.getFormulaBySubjectAndTeacherId(
                    _class.getSubject().getId(), _class.getTeacher().getId(), termId);
            cStudent = studentService.getStudentById(studentId);
            if(formula.getActivityPercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Activity> activityList = activityService.getActivityListByStudentId(studentId);
                            final double fActivity[] = new double[activityList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < activityList.size(); i++) {
                                if(activityList.get(i).get_class().getId() == classId) {
                                    final double total = activityList.get(i).getItemTotal();
                                    final double score = activityService
                                            .getActivityResultByActivityAndStudentId(activityList.get(i).getId(), studentId).getScore();
                                    fActivity[i] = (score / total) * 100;
                                    Log.i("Activity", fActivity[i] + "");
                                }
                            }
                            for(int i = 0; i < fActivity.length; i++)
                                tempTotal += fActivity[i];

                            tempTotal /= fActivity.length;
                            activityGrade = tempTotal;

                            gradeIsReadyAct = true;
                            notifyFinish();
                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                gradeIsReadyAct = true;
                notifyFinish();
            }
            if(formula.getAssignmentPercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Assignment> assignmentList = assignmentService.getAssignmentListByStudentId(studentId);
                            final double fAssignment[] = new double[assignmentList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < assignmentList.size(); i++) {
                                if(assignmentList.get(i).get_class().getId() == classId) {
                                    final double total = assignmentList.get(i).getItemTotal();
                                    final double score = assignmentService
                                            .getAssignmentResultByAssignmentAndStudentId(assignmentList.get(i).getId(), studentId).getScore();
                                    fAssignment[i] = (score / total) * 100;
                                    Log.i("Assignment", fAssignment[i] + "");
                                }
                            }
                            for(int i = 0; i < fAssignment.length; i++)
                                tempTotal += fAssignment[i];

                            tempTotal /= fAssignment.length;
                            assignmentGrade = tempTotal;

                            gradeIsReadyAss = true;
                            notifyFinish();

                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                gradeIsReadyAss = true;
                notifyFinish();
            }

            //
            if(formula.getAttendancePercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Attendance> attendanceList = attendanceService.getAttendanceListByStudentId(studentId);
                            final double fAttendance[] = new double[attendanceList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < attendanceList.size(); i++) {
                                if(attendanceList.get(i).get_class().getId() == classId) {
                                    final double total = 1;
                                    final double score = (attendanceService
                                            .getAttendanceResultByAttendanceAndStudentId(
                                                    attendanceList.get(i).getId(), studentId).getStatus() == 1 ? 1 : 0);
                                    fAttendance[i] = (score / total) * 100;
                                    Log.i("Attendance", fAttendance[i] + "");
                                }
                            }
                            for(int i = 0; i < fAttendance.length; i++)
                                tempTotal += fAttendance[i];

                            tempTotal /= fAttendance.length;
                            attendanceGrade = tempTotal;

                            gradeIsReadyAtt = true;
                            notifyFinish();
                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                gradeIsReadyAtt = true;
                notifyFinish();
            }

            //
            if(formula.getExamPercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Exam> examList = examService.getExamListByStudentId(studentId);
                            final double fExam[] = new double[examList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < examList.size(); i++) {
                                if(examList.get(i).get_class().getId() == classId) {
                                    final double total = examList.get(i).getItemTotal();
                                    final double score = examService
                                            .getExamResultByExamAndStudentId(examList.get(i).getId(), studentId).getScore();
                                    fExam[i] = (score / total) * 100;
                                    Log.i("Exam", fExam[i] + "");
                                }
                            }
                            for(int i = 0; i < fExam.length; i++)
                                tempTotal += fExam[i];

                            tempTotal /= fExam.length;
                            examGrade = tempTotal;

                            gradeIsReadyExa = true;
                            notifyFinish();
                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else{
                gradeIsReadyExa = true;
                notifyFinish();
            }


            //
            if(formula.getProjectPercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Project> projectList = projectService.getProjectListByStudentId(studentId);
                            final double fProject[] = new double[projectList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < projectList.size(); i++) {
                                if(projectList.get(i).get_class().getId() == classId) {
                                    final double total = projectList.get(i).getItemTotal();
                                    final double score = projectService
                                            .getProjectResultByProjectAndStudentId(projectList.get(i).getId(), studentId).getScore();
                                    fProject[i] = (score / total) * 100;
                                    Log.i("Project", fProject[i] + "");
                                }
                            }
                            for(int i = 0; i < fProject.length; i++)
                                tempTotal += fProject[i];

                            tempTotal /= fProject.length;
                            projectGrade = tempTotal;

                            gradeIsReadyPro = true;
                            notifyFinish();
                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else{
                gradeIsReadyPro = true;
                notifyFinish();
            }


            //
            if(formula.getQuizPercentage() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final List<Quiz> quizList = quizService.getQuizListByStudentId(studentId);
                            final double fQuiz[] = new double[quizList.size()];
                            double tempTotal = 0;

                            for (int i = 0; i < quizList.size(); i++) {
                                if(quizList.get(i).get_class().getId() == classId) {
                                    final double total = quizList.get(i).getItemTotal();
                                    final double score = quizService
                                            .getQuizResultByQuizAndStudentId(quizList.get(i).getId(), studentId).getScore();
                                    fQuiz[i] = (score / total) * 100;
                                    Log.i("Quiz", fQuiz[i] + "");
                                }
                            }
                            for(int i = 0; i < fQuiz.length; i++)
                                tempTotal += fQuiz[i];

                            tempTotal /= fQuiz.length;
                            quizGrade = tempTotal;

                            gradeIsReadyQui = true;
                            notifyFinish();
                        } catch (GradingFactorException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else{
                gradeIsReadyQui = true;
                notifyFinish();
            }

            //return new Grade(activityGrade);
            return  null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void notifyFinish() {
        if(!gradeIsReadyAct)
            return;
        if(!gradeIsReadyAss)
            return;
        if(!gradeIsReadyAtt)
            return;
        if(!gradeIsReadyExa)
            return;
        if(!gradeIsReadyPro)
            return;
        if(!gradeIsReadyQui)
            return;

        activityGrade *= formula.getActivityPercentage();
        assignmentGrade *= formula.getAssignmentPercentage();
        attendanceGrade *= formula.getAttendancePercentage();
        examGrade *= formula.getExamPercentage();
        projectGrade *= formula.getProjectPercentage();
        quizGrade *= formula.getQuizPercentage();

        double fgrade = activityGrade + assignmentGrade + attendanceGrade +
                examGrade + projectGrade + quizGrade;

        Grade grade = null;
//        try {
//            grade = new GradeServiceImpl().addGrade(new Grade(fgrade), classId, studentId, termId);
//        } catch (GradingFactorException e) {
//            e.printStackTrace();
//        }
        listener.setView(grade);
    }

    public interface Listener {
        void setView(Grade grade);
    }



}
