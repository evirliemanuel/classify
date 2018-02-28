package com.lieverandiver.thesisproject.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

public class TeacherHelper {

    private long id;
    private Context context;
    private Teacher teacher;

    public TeacherHelper(Context context) {
        this.context = context;
    }

    public TeacherHelper loadUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getLong("teacherId", 0);
        Log.i("aliceTAG", "Load : Teacher id = " + id);
        return this;
    }

    public TeacherHelper saveUser(long teacherId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("teacherId", teacherId);
        editor.apply();
        Log.i("aliceTAG", "Save : Teacher id = " + id);
        return this;
    }

    public TeacherHelper removeUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("teacherId", 0);
        editor.apply();
        return this;
    }

    public TeacherHelper saveUser(Teacher teacher) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("teacherId", teacher.getId());
        editor.apply();
        return this;
    }


    public boolean authenticate() {
        try {
            TeacherServiceImpl teacherService = new TeacherServiceImpl();
            if(teacherService.getTeacherById(id) != null)
                return true;
            else
                return false;
        } catch (TeacherException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Teacher get() {
        try {
            TeacherServiceImpl teacherService = new TeacherServiceImpl();
            return teacherService.getTeacherById(id);
        } catch (TeacherException e) {
            e.printStackTrace();
            return null;
        }
    }

}
