package com.evm.project.schapp.helper;

import android.util.Log;

import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.GradeService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.GradeServiceImpl;

import java.util.List;

public class ActivityGradeManager implements GradeManager, Runnable{

    private static final ActivityService activityService = new ActivityServiceImpl();
    private static final GradeService gradeService = new GradeServiceImpl();

    private Student student;
    private Grade grade;
    private List<Activity> activityList;

    public ActivityGradeManager(Student student, Grade grade, List<Activity> activityList) {
        this.student = student;
        this.grade = grade;
        this.activityList = activityList;
    }

    @Override
    public synchronized Grade compute() {
        new Thread(this).start();
        return grade;
    }

    @Override
    public void run() {
        try {
            //total activity grade
            double totalActivityGrade = 0;

            //total number of activity
            int numberOfActivity = activityList.size();

            for(Activity activity : activityList) {

                //activity total score
                int totalScore = activity.getItemTotal();

                //student score
                int score = activityService.getActivityResultByActivityAndStudentId(activity.getId(),
                        student.getId()).getScore();

                Log.i("Student : " + student.getId(), "TotalScore : "+  totalScore);
                Log.i("Student : " + student.getId(), "Score : "+  score);

                //equivalent score
                double tempScore = ((((double) score / (double) totalScore) * 100) * ACTUAL_GRADE) + (100 * DEFAULT_GRADE);

                //
                totalActivityGrade += tempScore;

                Log.i("Student : " + student.getId(), "Temp : "+  tempScore);
            }
            //real score
            totalActivityGrade /= (double) numberOfActivity;

            Log.i("Student : " + student.getId(), "Total : "+ totalActivityGrade);
            Log.i("Student : " + student.getId(), "Grade ID : "+  grade.getId());
            //
            grade.setActivityScore(totalActivityGrade);
            grade.setTotalScore(grade.getTotalScore() + totalActivityGrade);
            grade = gradeService.updateGradeById(grade.getId(), grade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





