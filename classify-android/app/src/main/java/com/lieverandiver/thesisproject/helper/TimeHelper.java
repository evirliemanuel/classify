package com.lieverandiver.thesisproject.helper;

import com.remswork.project.alice.model.support.Time;

import java.util.Calendar;

public class TimeHelper {

    private Time time;

    public TimeHelper() {
        super();
        time = new Time();
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public TimeHelper addHour(int hour) {
        time.setHour((time.getHour() + hour) % 24);
        return this;
    }

    public TimeHelper addMinute(int minute) {
        int hour = (time.getMinute() + minute) / 60;
        time.setMinute((time.getMinute() + minute) % 60);
        time.setHour((time.getHour() + hour) % 24);
        return this;
    }

    public TimeHelper addSecond(int second) {
        int minute = (time.getSecond() + second) / 60;
        int hour = (time.getMinute() + minute) / 60;
        time.setMinute((time.getMinute() + minute) % 60);
        time.setHour((time.getHour() + hour) % 24);
        return this;
    }

    public TimeHelper now() {
        Calendar calendar = Calendar.getInstance();
        time.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        time.setMinute(calendar.get(Calendar.MINUTE));
        time.setSecond(calendar.get(Calendar.SECOND));
        return this;
    }

    public TimeHelper reset() {
        time.setHour(0);
        time.setMinute(0);
        time.setSecond(0);
        return this;
    }

    public TimeHelper convert(String stringDate) {
        try {
            String pattern[] = {
                    "((0?[1-9])|(1[0-9])|(2[0-4])):((0?[0-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-9])" +
                            "|(5[0-9])):((0?[0-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-9])|(5[0-9]))",
                    "((0?[1-9])|(1[0-9])|(2[0-4])) hrs ((0?[0-9])|(1[0-9])" +
                            "|(2[0-9])|(3[0-9])|(4[0-9])|(5[0-9])) mins",
                    "((0?[1-9])|(1[012])):((0?[0-9])|(1[0-9])|(2[0-9])" +
                            "|(3[0-9])|(4[0-9])|(5[0-9])) [AP]M"};
            if (stringDate.matches(pattern[0])) {
                String t[] = stringDate.split(":");
                int hour = Integer.parseInt(t[0]);
                int minute = Integer.parseInt(t[1]);
                int second = Integer.parseInt(t[2]);

                time.setHour(hour);
                time.setMinute(minute);
                time.setSecond(second);
                return this;
            } else if(stringDate.matches(pattern[1])){
                String t[] = stringDate.split(" ");
                int hour = Integer.parseInt(t[0]);
                int minute = Integer.parseInt(t[2]);
                int second = 0;

                time.setHour(hour);
                time.setMinute(minute);
                time.setSecond(second);
                return this;
            } else if(stringDate.toUpperCase().matches(pattern[2])){
                String t[] = stringDate.split(":");
                String s[] = t[1].split(" ");
                String m = s[1];

                int hour = Integer.parseInt(t[0]);
                int minute = Integer.parseInt(s[0]);
                int second = 0;

                time.setHour((m.equals("PM") ? (hour == 12 ? 12 : hour + 12) : (hour == 12 ?
                        hour + 12 : hour)));
                time.setMinute(minute);
                time.setSecond(second);
                return this;
            } else
                throw new RuntimeException("Invalid time format");

        } catch (RuntimeException e) {
            e.printStackTrace();
            reset();
            return this;
        }
    }

    public boolean isValid(String stringDate) {
        String pattern[] = {
                "((0?[1-9])|(1[0-9])|(2[0-4])):((0?[0-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-9])" +
                        "|(5[0-9])):((0?[0-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-9])|(5[0-9]))",
                "((0?[1-9])|(1[0-9])|(2[0-4])) hrs ((0?[0-9])|(1[0-9])" +
                        "|(2[0-9])|(3[0-9])|(4[0-9])|(5[0-9])) mins",
                "((0?[1-9])|(1[012])):((0?[0-9])|(1[0-9])|(2[0-9])" +
                        "|(3[0-9])|(4[0-9])|(5[0-9])) [AP]M"};
        if (stringDate.matches(pattern[0])) {
            return true;
        }else if(stringDate.matches(pattern[1]))
            return true;
        else if(stringDate.matches(pattern[2]))
            return true;
        else
            return false;
    }
}