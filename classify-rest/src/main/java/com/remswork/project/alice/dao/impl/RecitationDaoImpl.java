package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.RecitationDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecitationDaoImpl implements RecitationDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Recitation getRecitationById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Recitation> recitationList = new ArrayList<>();
            Query query = session.createQuery("from Recitation");
            for (Object objRecitation : query.list())
                recitationList.add((Recitation) objRecitation);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Recitation> recitationList = new ArrayList<>();

            Query query = session.createQuery("from Recitation where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objRecitation : query.list())
                recitationList.add((Recitation) objRecitation);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Recitation> recitationList = new ArrayList<>();
            String hql = "from Recitation where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objRecitation : query.list())
                recitationList.add((Recitation) objRecitation);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Recitation> recitationList = new ArrayList<>();
            String hql = "from RecitationResult as R join R.recitation where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                recitationList.add((Recitation) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Recitation> recitationList = new ArrayList<>();
            String hql = "from RecitationResult as R join R.recitation as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Recitation's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                recitationList.add((Recitation) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public RecitationResult getRecitationResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            RecitationResult result = session.get(RecitationResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("RecitationResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public RecitationResult getRecitationResultByRecitationAndStudentId(long recitationId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, recitationId);
            Student student = session.get(Student.class, studentId);
            String hql = "from RecitationResult as R where R.recitation.id = :recitationId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("recitationId", recitationId);
            query.setParameter("studentId", studentId);

            if (recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + recitationId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No RecitationResult found. Try use query param " +
                        "(ex. studentId=[id])");

            RecitationResult result = (RecitationResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (recitation == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Recitation's class with id : " + classId + " does not exist");
            if (recitation.getTitle() == null)
                throw new GradingFactorDaoException("Recitation's title is required");
            if (recitation.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Recitation can't have an empty title");
            if (recitation.getDate() == null)
                throw new GradingFactorDaoException("Recitation's date is required");
            if (recitation.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Recitation can't have an empty date");
            if(recitation.getItemTotal() < 0)
                throw new GradingFactorDaoException("Recitation's itemTotal is invalid");

            recitation.set_class(_class);
            session.persist(recitation);
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (recitation == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Recitation's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Recitation's term with id : " + termId + " does not exist");
            if (recitation.getTitle() == null)
                throw new GradingFactorDaoException("Recitation's title is required");
            if (recitation.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Recitation can't have an empty title");
            if (recitation.getDate() == null)
                throw new GradingFactorDaoException("Recitation's date is required");
            if (recitation.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Recitation can't have an empty date");
            if(recitation.getItemTotal() < 0)
                throw new GradingFactorDaoException("Recitation's itemTotal is invalid");

            recitation.set_class(_class);
            recitation.setTerm(term);

            session.persist(recitation);
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public RecitationResult addRecitationResult(int score, long recitationId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, recitationId);
            Student student = session.get(Student.class, studentId);
            RecitationResult result = new RecitationResult();
            String hql = "from RecitationResult as R where R.recitation.id = :recitationId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("recitationId", recitationId);
            query.setParameter("studentId", studentId);

            if (recitation == null)
                throw new GradingFactorDaoException("Recitation's recitation with id : " + recitationId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");
            if (recitationId < 1)
                throw new GradingFactorDaoException("Query param : recitationId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > recitation.getItemTotal())
                throw new GradingFactorDaoException("RecitationResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("RecitationResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setRecitation(recitation);
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
    public Recitation updateRecitationById(long id, Recitation newRecitation, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            Class _class = session.get(Class.class, classId);

            if(newRecitation == null)
                newRecitation = new Recitation();
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Recitation's class with id : " + classId + " does not exist");
            if(!(newRecitation.getTitle() != null ? newRecitation.getTitle() : "").trim().isEmpty())
                recitation.setTitle(newRecitation.getTitle());
            if(!(newRecitation.getDate() != null ? newRecitation.getDate() : "").trim().isEmpty())
                recitation.setDate(newRecitation.getDate());
            if(classId > 0) {
                if(classId == (recitation.get_class() != null ? recitation.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Recitation's  class with id : " + classId + " already exist");
                recitation.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return recitation;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newRecitation == null)
                newRecitation = new Recitation();
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Recitation's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Recitation's term with id : " + termId + " does not exist");
            if(!(newRecitation.getTitle() != null ? newRecitation.getTitle() : "").trim().isEmpty())
                recitation.setTitle(newRecitation.getTitle());
            if(!(newRecitation.getDate() != null ? newRecitation.getDate() : "").trim().isEmpty())
                recitation.setDate(newRecitation.getDate());
            if(classId > 0) {
                if(classId == (recitation.get_class() != null ? recitation.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Recitation's  class with id : " + classId + " already exist");
                recitation.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (recitation.getTerm() != null ? recitation.getTerm().getId() : 0))
                    recitation.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return recitation;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public RecitationResult updateRecitationResultByRecitationAndStudentId(int score, long recitationId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, recitationId);
            Student student = session.get(Student.class, studentId);

            String hql = "from RecitationResult as R where R.recitation.id = :recitationId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("recitationId", recitationId);
            query.setParameter("studentId", studentId);

            if (recitation == null)
                throw new GradingFactorDaoException("Recitation's recitation with id : " + recitationId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");
            if (recitationId < 1)
                throw new GradingFactorDaoException("Query param : recitationId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            RecitationResult result = (RecitationResult) query.list().get(0);

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
    public Recitation deleteRecitationById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = RecitationResult.class.getSimpleName();

            Recitation recitation = session.get(Recitation.class, id);
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where recitation.id = :recitationId");
                Query query = session.createQuery(hql);
                query.setParameter("recitationId", id);
                query.executeUpdate();
            }
            session.delete(recitation);
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public RecitationResult deleteRecitationResultByRecitationAndStudentId(long recitationId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, recitationId);
            Student student = session.get(Student.class, studentId);

            String hql = "from RecitationResult as R where R.recitation.id = :recitationId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("recitationId", recitationId);
            query.setParameter("studentId", studentId);

            if (recitation == null)
                throw new GradingFactorDaoException("Recitation's recitation with id : " + recitationId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Recitation's student with id : " + studentId + " does not exist");
            if (recitationId < 1)
                throw new GradingFactorDaoException("Query param : recitationId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            RecitationResult result = (RecitationResult) query.list().get(0);

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