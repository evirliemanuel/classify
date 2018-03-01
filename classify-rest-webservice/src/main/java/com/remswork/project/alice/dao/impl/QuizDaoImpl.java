package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.QuizDao;
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
public class QuizDaoImpl implements QuizDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Quiz getQuizById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Quiz> quizList = new ArrayList<>();
            Query query = session.createQuery("from Quiz");
            for (Object objQuiz : query.list())
                quizList.add((Quiz) objQuiz);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Quiz> quizList = new ArrayList<>();

            Query query = session.createQuery("from Quiz where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objQuiz : query.list())
                quizList.add((Quiz) objQuiz);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Quiz> quizList = new ArrayList<>();
            String hql = "from Quiz where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objQuiz : query.list())
                quizList.add((Quiz) objQuiz);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Quiz> quizList = new ArrayList<>();
            String hql = "from QuizResult as R join R.quiz where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                quizList.add((Quiz) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Quiz> quizList = new ArrayList<>();
            String hql = "from QuizResult as R join R.quiz as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Quiz's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                quizList.add((Quiz) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public QuizResult getQuizResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            QuizResult result = session.get(QuizResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("QuizResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public QuizResult getQuizResultByQuizAndStudentId(long quizId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, quizId);
            Student student = session.get(Student.class, studentId);
            String hql = "from QuizResult as R where R.quiz.id = :quizId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("studentId", studentId);

            if (quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + quizId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No QuizResult found. Try use query param " +
                        "(ex. studentId=[id])");

            QuizResult result = (QuizResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (quiz == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Quiz's class with id : " + classId + " does not exist");
            if (quiz.getTitle() == null)
                throw new GradingFactorDaoException("Quiz's title is required");
            if (quiz.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Quiz can't have an empty title");
            if (quiz.getDate() == null)
                throw new GradingFactorDaoException("Quiz's date is required");
            if (quiz.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Quiz can't have an empty date");
            if(quiz.getItemTotal() < 0)
                throw new GradingFactorDaoException("Quiz's itemTotal is invalid");

            quiz.set_class(_class);
            session.persist(quiz);
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (quiz == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Quiz's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Quiz's term with id : " + termId + " does not exist");
            if (quiz.getTitle() == null)
                throw new GradingFactorDaoException("Quiz's title is required");
            if (quiz.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Quiz can't have an empty title");
            if (quiz.getDate() == null)
                throw new GradingFactorDaoException("Quiz's date is required");
            if (quiz.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Quiz can't have an empty date");
            if(quiz.getItemTotal() < 0)
                throw new GradingFactorDaoException("Quiz's itemTotal is invalid");

            quiz.set_class(_class);
            quiz.setTerm(term);

            session.persist(quiz);
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public QuizResult addQuizResult(int score, long quizId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, quizId);
            Student student = session.get(Student.class, studentId);
            QuizResult result = new QuizResult();
            String hql = "from QuizResult as R where R.quiz.id = :quizId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("studentId", studentId);

            if (quiz == null)
                throw new GradingFactorDaoException("Quiz's quiz with id : " + quizId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");
            if (quizId < 1)
                throw new GradingFactorDaoException("Query param : quizId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > quiz.getItemTotal())
                throw new GradingFactorDaoException("QuizResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("QuizResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setQuiz(quiz);
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
    public Quiz updateQuizById(long id, Quiz newQuiz, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            Class _class = session.get(Class.class, classId);

            if(newQuiz == null)
                newQuiz = new Quiz();
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Quiz's class with id : " + classId + " does not exist");
            if(!(newQuiz.getTitle() != null ? newQuiz.getTitle() : "").trim().isEmpty())
                quiz.setTitle(newQuiz.getTitle());
            if(!(newQuiz.getDate() != null ? newQuiz.getDate() : "").trim().isEmpty())
                quiz.setDate(newQuiz.getDate());
            if(classId > 0) {
                if(classId == (quiz.get_class() != null ? quiz.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Quiz's  class with id : " + classId + " already exist");
                quiz.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return quiz;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newQuiz == null)
                newQuiz = new Quiz();
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Quiz's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Quiz's term with id : " + termId + " does not exist");
            if(!(newQuiz.getTitle() != null ? newQuiz.getTitle() : "").trim().isEmpty())
                quiz.setTitle(newQuiz.getTitle());
            if(!(newQuiz.getDate() != null ? newQuiz.getDate() : "").trim().isEmpty())
                quiz.setDate(newQuiz.getDate());
            if(classId > 0) {
                if(classId == (quiz.get_class() != null ? quiz.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Quiz's  class with id : " + classId + " already exist");
                quiz.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (quiz.getTerm() != null ? quiz.getTerm().getId() : 0))
                    quiz.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return quiz;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public QuizResult updateQuizResultByQuizAndStudentId(int score, long quizId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, quizId);
            Student student = session.get(Student.class, studentId);

            String hql = "from QuizResult as R where R.quiz.id = :quizId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("studentId", studentId);

            if (quiz == null)
                throw new GradingFactorDaoException("Quiz's quiz with id : " + quizId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");
            if (quizId < 1)
                throw new GradingFactorDaoException("Query param : quizId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            QuizResult result = (QuizResult) query.list().get(0);

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
    public Quiz deleteQuizById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = QuizResult.class.getSimpleName();

            Quiz quiz = session.get(Quiz.class, id);
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where quiz.id = :quizId");
                Query query = session.createQuery(hql);
                query.setParameter("quizId", id);
                query.executeUpdate();
            }
            session.delete(quiz);
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public QuizResult deleteQuizResultByQuizAndStudentId(long quizId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, quizId);
            Student student = session.get(Student.class, studentId);

            String hql = "from QuizResult as R where R.quiz.id = :quizId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("studentId", studentId);

            if (quiz == null)
                throw new GradingFactorDaoException("Quiz's quiz with id : " + quizId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Quiz's student with id : " + studentId + " does not exist");
            if (quizId < 1)
                throw new GradingFactorDaoException("Query param : quizId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            QuizResult result = (QuizResult) query.list().get(0);

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