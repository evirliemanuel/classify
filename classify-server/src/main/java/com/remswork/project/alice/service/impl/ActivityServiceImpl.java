package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ActivityDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDaoImpl activityDao;


    @Override
    public Activity getActivityById(long id) throws GradingFactorException {
        return activityDao.getActivityById(id);
    }

    @Override
    public List<Activity> getActivityList() throws GradingFactorException {
        return activityDao.getActivityList();
    }

    @Override
    public List<Activity> getActivityListByClassId(long classId) throws GradingFactorException {
        return activityDao.getActivityListByClassId(classId);
    }

    @Override
    public List<Activity> getActivityListByClassId(long classId, long termId) throws GradingFactorException {
        return activityDao.getActivityListByClassId(classId, termId);
    }

    @Override
    public List<Activity> getActivityListByStudentId(long studentId) throws GradingFactorException {
        return activityDao.getActivityListByStudentId(studentId);
    }

    @Override
    public List<Activity> getActivityListByStudentId(long studentId, long termId) throws GradingFactorException {
        return activityDao.getActivityListByStudentId(studentId, termId);
    }

    @Override
    public ActivityResult getActivityResultById(long id) throws GradingFactorException {
        return activityDao.getActivityResultById(id);
    }

    @Override
    public ActivityResult getActivityResultByActivityAndStudentId(long activityId, long studentId) throws GradingFactorException {
        return activityDao.getActivityResultByActivityAndStudentId(activityId, studentId);
    }

    @Override
    public Activity addActivity(Activity activity, long classId) throws GradingFactorException {
        return activityDao.addActivity(activity, classId);
    }

    @Override
    public Activity addActivity(Activity activity, long classId, long termId) throws GradingFactorException {
        return activityDao.addActivity(activity, classId, termId);
    }

    @Override
    public ActivityResult addActivityResult(int score, long activityId, long studentId) throws GradingFactorException {
        return activityDao.addActivityResult(score, activityId, studentId);
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long classId) throws GradingFactorException {
        return activityDao.updateActivityById(id, newActivity, classId);
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long classId, long termId)
            throws GradingFactorException {
        return activityDao.updateActivityById(id, newActivity, classId, termId);
    }

    @Override
    public ActivityResult updateActivityResultByActivityAndStudentId(int score, long activityId, long studentId)
            throws GradingFactorException {
        return activityDao.updateActivityResultByActivityAndStudentId(score, activityId, studentId);
    }

    @Override
    public Activity deleteActivityById(long id) throws GradingFactorException {
        return activityDao.deleteActivityById(id);
    }

    @Override
    public ActivityResult deleteActivityResultByActivityAndStudentId(long activityId, long studentId)
            throws GradingFactorException {
        return activityDao.deleteActivityResultByActivityAndStudentId(activityId, studentId);
    }
}
