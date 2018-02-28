package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ScheduleDaoImpl;
import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    @Override
    public Schedule getScheduleById(long id) throws ScheduleException {
        return scheduleDao.getScheduleById(id);
    }

    @Override
    public List<Schedule> getScheduleList() throws ScheduleException {
        return scheduleDao.getScheduleList();
    }

    @Override
    public Set<Schedule> getScheduleListByTeacherId(long teacherId) throws ScheduleException {
        return scheduleDao.getScheduleListByTeacherId(teacherId);
    }

    @Override
    public Schedule addSchedule(Schedule schedule) throws ScheduleException {
        return scheduleDao.addSchedule(schedule);
    }

    @Override
    public Schedule updateScheduleById(long id, Schedule newSchedule) throws ScheduleException {
        return scheduleDao.updateScheduleById(id, newSchedule);
    }

    @Override
    public Schedule deleteScheduleById(long id) throws ScheduleException {
        return scheduleDao.deleteScheduleById(id);
    }
}
