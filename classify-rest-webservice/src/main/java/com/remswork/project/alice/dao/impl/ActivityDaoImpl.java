package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ActivityDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.dao.exception.StudentDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.model.Class;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDaoImpl implements ActivityDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Activity getActivityById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Activity> activityList = new ArrayList<>();
            Query query = session.createQuery("from Activity");
            for (Object objActivity : query.list())
                activityList.add((Activity) objActivity);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Activity> activityList = new ArrayList<>();

            Query query = session.createQuery("from Activity where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objActivity : query.list())
                activityList.add((Activity) objActivity);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Activity> activityList = new ArrayList<>();
            String hql = "from Activity where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objActivity : query.list())
                activityList.add((Activity) objActivity);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Activity> activityList = new ArrayList<>();
            String hql = "from ActivityResult as R join R.activity where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                activityList.add((Activity) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Activity> activityList = new ArrayList<>();
            String hql = "from ActivityResult as R join R.activity as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Activity's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                activityList.add((Activity) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ActivityResult getActivityResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            ActivityResult result = session.get(ActivityResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("ActivityResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ActivityResult getActivityResultByActivityAndStudentId(long activityId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, activityId);
            Student student = session.get(Student.class, studentId);
            String hql = "from ActivityResult as R where R.activity.id = :activityId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("activityId", activityId);
            query.setParameter("studentId", studentId);

            if (activity == null)
                throw new GradingFactorDaoException("Activity with id : " + activityId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No ActivityResult found. Try use query param " +
                        "(ex. studentId=[id])");

            ActivityResult result = (ActivityResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity addActivity(Activity activity, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (activity == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Activity's class with id : " + classId + " does not exist");
            if (activity.getTitle() == null)
                throw new GradingFactorDaoException("Activity's title is required");
            if (activity.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Activity can't have an empty title");
            if (activity.getDate() == null)
                throw new GradingFactorDaoException("Activity's date is required");
            if (activity.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Activity can't have an empty date");
            if(activity.getItemTotal() < 0)
                throw new GradingFactorDaoException("Activity's itemTotal is invalid");

            activity.set_class(_class);
            session.persist(activity);
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity addActivity(Activity activity, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (activity == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Activity's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Activity's term with id : " + termId + " does not exist");
            if (activity.getTitle() == null)
                throw new GradingFactorDaoException("Activity's title is required");
            if (activity.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Activity can't have an empty title");
            if (activity.getDate() == null)
                throw new GradingFactorDaoException("Activity's date is required");
            if (activity.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Activity can't have an empty date");
            if(activity.getItemTotal() < 0)
                throw new GradingFactorDaoException("Activity's itemTotal is invalid");

            activity.set_class(_class);
            activity.setTerm(term);

            session.persist(activity);
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ActivityResult addActivityResult(int score, long activityId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, activityId);
            Student student = session.get(Student.class, studentId);
            ActivityResult result = new ActivityResult();
            String hql = "from ActivityResult as R where R.activity.id = :activityId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("activityId", activityId);
            query.setParameter("studentId", studentId);

            if (activity == null)
                throw new GradingFactorDaoException("Activity's activity with id : " + activityId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");
            if (activityId < 1)
                throw new GradingFactorDaoException("Query param : activityId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > activity.getItemTotal())
                throw new GradingFactorDaoException("ActivityResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("ActivityResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setActivity(activity);
            result.setStudent(student);

            session.persist(result);
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            Class _class = session.get(Class.class, classId);

            if(newActivity == null)
                newActivity = new Activity();
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Activity's class with id : " + classId + " does not exist");
            if(!(newActivity.getTitle() != null ? newActivity.getTitle() : "").trim().isEmpty())
                activity.setTitle(newActivity.getTitle());
            if(!(newActivity.getDate() != null ? newActivity.getDate() : "").trim().isEmpty())
                activity.setDate(newActivity.getDate());
            if(classId > 0) {
                if(classId == (activity.get_class() != null ? activity.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Activity's  class with id : " + classId + " already exist");
                activity.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return activity;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newActivity == null)
                newActivity = new Activity();
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Activity's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Activity's term with id : " + termId + " does not exist");
            if(!(newActivity.getTitle() != null ? newActivity.getTitle() : "").trim().isEmpty())
                activity.setTitle(newActivity.getTitle());
            if(!(newActivity.getDate() != null ? newActivity.getDate() : "").trim().isEmpty())
                activity.setDate(newActivity.getDate());
            if(classId > 0) {
                if(classId == (activity.get_class() != null ? activity.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Activity's  class with id : " + classId + " already exist");
                activity.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (activity.getTerm() != null ? activity.getTerm().getId() : 0))
                    activity.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return activity;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ActivityResult updateActivityResultByActivityAndStudentId(int score, long activityId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, activityId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ActivityResult as R where R.activity.id = :activityId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("activityId", activityId);
            query.setParameter("studentId", studentId);

            if (activity == null)
                throw new GradingFactorDaoException("Activity's activity with id : " + activityId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");
            if (activityId < 1)
                throw new GradingFactorDaoException("Query param : activityId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to update");
            ActivityResult result = (ActivityResult) query.list().get(0);

            result.setScore(score);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity deleteActivityById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = ActivityResult.class.getSimpleName();

            Activity activity = session.get(Activity.class, id);
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where activity.id = :activityId");
                Query query = session.createQuery(hql);
                query.setParameter("activityId", id);
                query.executeUpdate();
            }
            session.delete(activity);
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ActivityResult deleteActivityResultByActivityAndStudentId(long activityId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, activityId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ActivityResult as R where R.activity.id = :activityId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("activityId", activityId);
            query.setParameter("studentId", studentId);

            if (activity == null)
                throw new GradingFactorDaoException("Activity's activity with id : " + activityId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Activity's student with id : " + studentId + " does not exist");
            if (activityId < 1)
                throw new GradingFactorDaoException("Query param : activityId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
                ActivityResult result = (ActivityResult) query.list().get(0);

            session.delete(result);
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
