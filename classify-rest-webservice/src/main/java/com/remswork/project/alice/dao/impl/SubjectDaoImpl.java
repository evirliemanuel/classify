package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.SubjectDao;
import com.remswork.project.alice.dao.exception.SubjectDaoException;
import com.remswork.project.alice.exception.SubjectException;
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
public class SubjectDaoImpl implements SubjectDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Subject getSubjectById(long id) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Subject subject = session.get(Subject.class, id);
            if(subject == null)
                throw new SubjectDaoException("SubjectDto with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return subject;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public Subject getSubjectByClassAndTeacherId(long classId, long teacherId) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String hql = "from Class as c join c.subject as su join c.teacher as t where c.id = :classId and " +
                    "t.id = :teacherId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("teacherId", teacherId);

            if(query.list().size() < 1)
                throw new SubjectDaoException("No subject found with teacherId : " + teacherId);

            Subject subject = (Subject) ((Object[]) query.list().get(0))[1];
            session.getTransaction().commit();
            session.close();
            return subject;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public Subject getSubjectByScheduleId(long scheduleId) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String hql = "from Class as c join c.subject as su join c.scheduleList as sc where sc.id = :scheduleId";
            Query query = session.createQuery(hql);
            query.setParameter("scheduleId", scheduleId);

            if(query.list().size() < 1)
                throw new SubjectDaoException("No subject found with scheduleId : " + scheduleId);
            Subject subject = (Subject) ((Object[]) query.list().get(0))[1];
            session.getTransaction().commit();
            session.close();
            return subject;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public List<Subject> getSubjectList() throws SubjectException {
        List<Subject> subjectList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("from Subject");
            for(Object subjectObj : query.list())
                subjectList.add((Subject) subjectObj);
            session.getTransaction().commit();
            session.close();
            return subjectList;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public List<Subject> getSubjectListByTeacherId(long teacherId) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Subject> subjectList = new ArrayList<>();
            String hql = "from Class as c join c.subject as s join c.teacher as t where t.id = :teacherId";
            Query query = session.createQuery(hql);
            query.setParameter("teacherId", teacherId);

            for(Object list : query.list()) {
                Object[] row = (Object[]) list;
                subjectList.add((Subject) row[1]);
            }
            session.getTransaction().commit();
            session.close();
            return subjectList;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    public List<Subject> getSubjectListByTeacherIdUnique(long teacherId) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Subject> subjectList = new ArrayList<>();
            String hql = "from Class as c join c.subject as s join c.teacher as t where t.id = :teacherId group by s.id";
            Query query = session.createQuery(hql);
            query.setParameter("teacherId", teacherId);

            for(Object list : query.list()) {
                Object[] row = (Object[]) list;
                subjectList.add((Subject) row[1]);
            }
            session.getTransaction().commit();
            session.close();
            return subjectList;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public List<Subject> getSubjectListByStudentId(long studentId) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Subject> subjectList = new ArrayList<>();
            String hql = "from Class as c join c.subject as su join c.studentList as st where st.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);

            for(Object list : query.list()) {
                Object[] row = (Object[]) list;
                subjectList.add((Subject) row[1]);
            }
            session.getTransaction().commit();
            session.close();
            return subjectList;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public Subject addSubject(Subject subject) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (subject == null)
                throw new SubjectDaoException("You tried to add subject with a null value");
            if (subject.getName() == null)
                throw new SubjectDaoException("SubjectDto's name is required");
            if (subject.getName().trim().equals(""))
                throw new SubjectDaoException("SubjectDto can't have an empty name");
            if (subject.getDescription() == null)
                throw new SubjectDaoException("SubjectDto's description is required");
            if (subject.getDescription().trim().equals(""))
                throw new SubjectDaoException("SubjectDto can't have an empty description");
            if (subject.getCode() == null)
                throw new SubjectDaoException("SubjectDto's code is required");
            if (subject.getCode().trim().equals(""))
                throw new SubjectDaoException("SubjectDto can't have an empty code");
            if (subject.getUnit() == 0)
                throw new SubjectDaoException("SubjectDto's unit is required");
            if (subject.getUnit() < 0)
                throw new SubjectDaoException("SubjectDto's unit is invalid");

            session.persist(subject);
            session.getTransaction().commit();
            session.close();
            return subject;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public Subject updateSubjectById(long id, Subject newSubject) throws SubjectException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Subject subject = session.get(Subject.class, id);
            if(newSubject == null)
                throw new SubjectDaoException("You tried to update subject with a null value");
            if(subject == null)
                throw new SubjectDaoException("SubjectDto with id : " + id + " does not exist");
            if (!(newSubject.getName() != null ? newSubject.getName() : "").trim().isEmpty())
                subject.setName(newSubject.getName());
            if (!(newSubject.getDescription() != null ? newSubject.getDescription() : "").trim().isEmpty())
                subject.setDescription(newSubject.getDescription());
            if (!(newSubject.getCode() != null ? newSubject.getCode() : "").trim().isEmpty())
                subject.setCode(newSubject.getCode());
            if (newSubject.getUnit() != 0) {
                if(newSubject.getUnit() < 0)
                    throw new SubjectDaoException("SubjectDto's unit is invalid");
                subject.setUnit(newSubject.getUnit());
            }
            session.getTransaction().commit();
            session.close();
            return subject;
        }catch (SubjectDaoException e) {
            session.close();
            throw new SubjectException(e.getMessage());
        }
    }

    @Override
    public Subject deleteSubjectById(long id) throws SubjectException {
       Session session = sessionFactory.openSession();
       session.beginTransaction();
       try {
           String[] table = new String[2];
           table[0] = Class.class.getSimpleName();
           table[1] = Formula.class.getSimpleName();

           Subject subject = session.get(Subject.class, id);
           if(subject == null)
               throw new SubjectDaoException("SubjectDto with id : " + id + " does not exist");

           for(String cell : table) {
               String hql = "delete from ".concat(cell).concat(" as a where a.subject.id = :subjectId");
               Query query = session.createQuery(hql);
               query.setParameter("subjectId", id);
               query.executeUpdate();
           }

           session.delete(subject);
           session.getTransaction().commit();
           session.close();
           return subject;
       }catch (SubjectDaoException e) {
           session.close();
           throw new SubjectException(e.getMessage());
       }
    }
}
