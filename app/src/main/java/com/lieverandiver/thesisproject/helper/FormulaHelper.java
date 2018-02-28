package com.lieverandiver.thesisproject.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

public class FormulaHelper {

    private long id;
    private Context context;
    private Teacher teacher;

    public FormulaHelper(Context context) {
        this.context = context;
    }

    public Formula getFormula(String key) throws GradingFactorException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getLong(key, 0);
        return new FormulaServiceImpl().getFormulaById(id);
    }

    public FormulaHelper saveUser(String key, long valueId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aliceUserDetail",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, valueId);
        editor.apply();
        return this;
    }


}
