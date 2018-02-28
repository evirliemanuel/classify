package com.remswork.project.alice.model.support;

import java.util.Calendar;
import java.util.Locale;

@Deprecated
public class Date {

    private int day;
    private int month;
    private int year;

    public Date() {
        super();
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date now() {
        Calendar calendar = Calendar.getInstance();
        setDay(calendar.get(Calendar.DAY_OF_MONTH));
        setMonth(calendar.get(Calendar.MONTH)+1);
        setYear(calendar.get(Calendar.YEAR));
        return this;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%d/%d/%d", getMonth(), getDay(), getYear());
    }


}
