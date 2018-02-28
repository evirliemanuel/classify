package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ExamDao;
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
public class ExamDaoImpl implements ExamDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Exam getExamById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();
            Query query = session.createQuery("from Exam");
            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();

            Query query = session.createQuery("from Exam where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();
            String hql = "from Exam where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Exam> examList = new ArrayList<>();
            String hql = "from ExamResult as R join R.exam where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                examList.add((Exam) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Exam> examList = new ArrayList<>();
            String hql = "from ExamResult as R join R.exam as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Exam's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                examList.add((Exam) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ExamResult getExamResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            ExamResult result = session.get(ExamResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("ExamResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ExamResult getExamResultByExamAndStudentId(long examId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, examId);
            Student student = session.get(Student.class, studentId);
            String hql = "from ExamResult as R where R.exam.id = :examId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("examId", examId);
            query.setParameter("studentId", studentId);

            if (exam == null)
                throw new GradingFactorDaoException("Exam with id : " + examId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No ExamResult found. Try use query param " +
                        "(ex. studentId=[id])");

            ExamResult result = (ExamResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam addExam(Exam exam, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (exam == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Exam's class with id : " + classId + " does not exist");
            if (exam.getTitle() == null)
                throw new GradingFactorDaoException("Exam's title is required");
            if (exam.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Exam can't have an empty title");
            if (exam.getDate() == null)
                throw new GradingFactorDaoException("Exam's date is required");
            if (exam.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Exam can't have an empty date");
            if(exam.getItemTotal() < 0)
                throw new GradingFactorDaoException("Exam's itemTotal is invalid");

            exam.set_class(_class);
            session.persist(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam addExam(Exam exam, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (exam == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Exam's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Exam's term with id : " + termId + " does not exist");
            if (exam.getTitle() == null)
                throw new GradingFactorDaoException("Exam's title is required");
            if (exam.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Exam can't have an empty title");
            if (exam.getDate() == null)
                throw new GradingFactorDaoException("Exam's date is required");
            if (exam.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Exam can't have an empty date");
            if(exam.getItemTotal() < 0)
                throw new GradingFactorDaoException("Exam's itemTotal is invalid");

            exam.set_class(_class);
            exam.setTerm(term);

            session.persist(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ExamResult addExamResult(int score, long examId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, examId);
            Student student = session.get(Student.class, studentId);
            ExamResult result = new ExamResult();
            String hql = "from ExamResult as R where R.exam.id = :examId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("examId", examId);
            query.setParameter("studentId", studentId);

            if (exam == null)
                throw new GradingFactorDaoException("Exam's exam with id : " + examId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");
            if (examId < 1)
                throw new GradingFactorDaoException("Query param : examId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > exam.getItemTotal())
                throw new GradingFactorDaoException("ExamResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("ExamResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setExam(exam);
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
    public Exam updateExamById(long id, Exam newExam, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            Class _class = session.get(Class.class, classId);

            if(newExam == null)
                newExam = new Exam();
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Exam's class with id : " + classId + " does not exist");
            if(!(newExam.getTitle() != null ? newExam.getTitle() : "").trim().isEmpty())
                exam.setTitle(newExam.getTitle());
            if(!(newExam.getDate() != null ? newExam.getDate() : "").trim().isEmpty())
                exam.setDate(newExam.getDate());
            if(classId > 0) {
                if(classId == (exam.get_class() != null ? exam.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Exam's  class with id : " + classId + " already exist");
                exam.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return exam;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newExam == null)
                newExam = new Exam();
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Exam's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Exam's term with id : " + termId + " does not exist");
            if(!(newExam.getTitle() != null ? newExam.getTitle() : "").trim().isEmpty())
                exam.setTitle(newExam.getTitle());
            if(!(newExam.getDate() != null ? newExam.getDate() : "").trim().isEmpty())
                exam.setDate(newExam.getDate());
            if(classId > 0) {
                if(classId == (exam.get_class() != null ? exam.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Exam's  class with id : " + classId + " already exist");
                exam.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (exam.getTerm() != null ? exam.getTerm().getId() : 0))
                    exam.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return exam;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ExamResult updateExamResultByExamAndStudentId(int score, long examId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, examId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ExamResult as R where R.exam.id = :examId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("examId", examId);
            query.setParameter("studentId", studentId);

            if (exam == null)
                throw new GradingFactorDaoException("Exam's exam with id : " + examId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");
            if (examId < 1)
                throw new GradingFactorDaoException("Query param : examId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            ExamResult result = (ExamResult) query.list().get(0);

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
    public Exam deleteExamById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = ExamResult.class.getSimpleName();

            Exam exam = session.get(Exam.class, id);
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where exam.id = :examId");
                Query query = session.createQuery(hql);
                query.setParameter("examId", id);
                query.executeUpdate();
            }
            session.delete(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ExamResult deleteExamResultByExamAndStudentId(long examId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, examId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ExamResult as R where R.exam.id = :examId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("examId", examId);
            query.setParameter("studentId", studentId);

            if (exam == null)
                throw new GradingFactorDaoException("Exam's exam with id : " + examId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Exam's student with id : " + studentId + " does not exist");
            if (examId < 1)
                throw new GradingFactorDaoException("Query param : examId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            ExamResult result = (ExamResult) query.list().get(0);

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
