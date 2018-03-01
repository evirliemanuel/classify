package com.remswork.project.alice.model.support;

import java.util.Locale;

public class Time {

    private int hour;
    private int minute;
    private int second;
    private Style style;

    public Time() {
        style = style.DEFAULT;
    }

    public Time(int hour, int minute, int second) {
        this();
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return style.format(this);
    }

    public String toString(Style style) {
        return style.format(this);
    }

    public enum Style {

        NORMAL("normal"), MILITARY("military"), PERIOD("period"), DEFAULT("default");

        private String type;

        Style(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String format(Time time) {
            if(type.equals("normal")) {
                return String.format(Locale.ENGLISH,
                        "%02d:%02d %s",
                        (time.getHour() != 12 ? (time.getHour() != 24 ? time.getHour() % 12 : 12) : 12),
                        time.getMinute(), (time.getHour() > 11) ? (time.getHour() < 24 ? "PM" : "AM") : "AM");
            } else if(type.equals("military")) {
                return String.format(Locale.ENGLISH,
                        "%02d:%02d:%02d",
                        time.getHour(),
                        time.getMinute(),
                        time.getSecond());
            } else if(type.equals("period")) {
                return String.format(Locale.ENGLISH,
                        "%d hrs %d mins",
                        time.getHour(),
                        time.getMinute());
            } else
                return "{hour=" + time.getHour() +
                        ", minute=" + time.getMinute() +
                        ", second=" + time.getSecond() + '}';
        }
    }

}
