package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;

import java.util.List;
import java.util.Set;

public interface ScheduleDao {

    Schedule getScheduleById(long id) throws ScheduleException;

    List<Schedule> getScheduleList() throws ScheduleException;

    Set<Schedule> getScheduleListByTeacherId(long teacherId) throws ScheduleException;

    Schedule addSchedule(Schedule schedule) throws ScheduleException;

    Schedule updateScheduleById(long id, Schedule newSchedule) throws ScheduleException;

    Schedule deleteScheduleById(long id) throws ScheduleException;

}
