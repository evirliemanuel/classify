package io.ermdev.alice.dto;

import io.ermdev.alice.entity.Class;
import mapfierj.MapTo;

import java.sql.Time;

public class ScheduleDto {

    private Long id;
    private String day;
    private Time startTime;
    private Time endTime;
    private Double hours;
    private String room;

    @MapTo(ClassDto.class)
    private Class _class ;

    public ScheduleDto(){}
}
