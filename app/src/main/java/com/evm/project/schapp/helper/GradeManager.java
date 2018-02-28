package com.evm.project.schapp.helper;

import com.remswork.project.alice.model.Grade;

public interface GradeManager {

    public static final double DEFAULT_GRADE = 65.0 / 100.0;
    public static final double ACTUAL_GRADE = 35.0 / 100.0;

    Grade compute();
}
