package io.classify.model;

import com.remswork.project.alice.model.Grade;

public class Mark {

    public Grade midterm;
    public Grade finalterm;

    public Grade getMidterm() {
        return midterm;
    }

    public void setMidterm(Grade midterm) {
        this.midterm = midterm;
    }

    public Grade getFinalterm() {
        return finalterm;
    }

    public void setFinalterm(Grade finalterm) {
        this.finalterm = finalterm;
    }
}
