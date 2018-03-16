package com.evm.project.schapp.helper;

public class GradeHelper {

    public static String calculate(double gr) {
        if (gr >= 99) {
            return "1.0";
        } else {
            return "5.0";
        }
    }
}
