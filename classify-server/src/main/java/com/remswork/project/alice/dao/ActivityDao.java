package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.ActivityResult;

import java.util.List;

public interface ActivityDao {

    Activity getActivityById(long id) throws GradingFactorException;

    List<Activity> getActivityList() throws GradingFactorException;

    List<Activity> getActivityListByClassId(long classId) throws GradingFactorException;

    List<Activity> getActivityListByClassId(long classId, long termId) throws GradingFactorException;

    List<Activity> getActivityListByStudentId(long studentId) throws GradingFactorException;

    List<Activity> getActivityListByStudentId(long studentId, long termId) throws GradingFactorException;

    ActivityResult getActivityResultById(long id) throws GradingFactorException;

    ActivityResult getActivityResultByActivityAndStudentId(long activityId, long studentId)
            throws GradingFactorException;

    Activity addActivity(Activity activity, long classId) throws GradingFactorException;

    Activity addActivity(Activity activity, long classId, long termId) throws GradingFactorException;

    ActivityResult addActivityResult(int score, long activityId, long studentId) throws GradingFactorException;

    Activity updateActivityById(long id, Activity newActivity, long classId)
            throws GradingFactorException;

    Activity updateActivityById(long id, Activity newActivity, long classId, long termId)
            throws GradingFactorException;

    ActivityResult updateActivityResultByActivityAndStudentId(int score, long activityId, long studentId)
            throws GradingFactorException;

    Activity deleteActivityById(long activityId) throws GradingFactorException;

    ActivityResult deleteActivityResultByActivityAndStudentId(long activityId, long studentId)
            throws GradingFactorException;
}
