package com.evm.project.schapp.helper;

import com.remswork.project.alice.model.Grade;

public class GradingFactory {

    public static final double DEFAULT_GRADE = 65 / 100;
    public static final double ACTUAL_GRADE = 35 / 100;

    private GradeManager manager;

    public GradingFactory(GradeManager manager) {
        this.manager  = manager;
    }

    public Grade getGrade() {
        return null;
    }
}
