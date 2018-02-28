package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.FormulaDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.Term;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FormulaDaoImpl implements FormulaDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Formula getFormulaById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Formula formula = session.get(Formula.class, id);
            if (formula == null)
                throw new GradingFactorDaoException("Formula with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Formula getFormulaBySubjectAndTeacherId(long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String hql = "from Formula where subject.id = :subjectId and teacher.id = :teacherId and term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("subjectId", subjectId);
            query.setParameter("teacherId", teacherId);
            query.setParameter("termId", termId);
            Formula formula;

            if (query.list().size() < 1)
                throw new GradingFactorDaoException("Formula with subjectId : " + subjectId + ", teacherId : "
                        + teacherId + " or termId : " + termId + " does not exist");
            formula = (Formula) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Formula> getFormulaList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Formula> formulaList = new ArrayList<>();
            Query query = session.createQuery("from Formula");
            for (Object objFormula : query.list())
                formulaList.add((Formula) objFormula);
            session.getTransaction().commit();
            session.close();
            return formulaList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Formula> getFormulaListByTeacherId(long teacherId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Formula> formulaList = new ArrayList<>();
            String hql = "from Formula where teacher.id = :teacherId";
            Query query = session.createQuery(hql);
            query.setParameter("teacherId", teacherId);

            for (Object objFormula : query.list())
                formulaList.add((Formula) objFormula);
            session.getTransaction().commit();
            session.close();
            return formulaList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Formula addFormula(Formula formula, long subjectId, long teacherId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Subject subject = session.get(Subject.class, subjectId);
            Teacher teacher = session.get(Teacher.class, teacherId);
            if (teacherId == 0)
                throw new GradingFactorException("Query param : teacherId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (formula == null)
                throw new GradingFactorDaoException("You tried to add formula with a null value");
            if (subject == null)
                throw new GradingFactorDaoException("Factor's subject with id : " + subjectId + " does not exist");
            if (teacher == null)
                throw new GradingFactorDaoException("Factor's teacher with id : " + teacherId + " does not exist");

            formula.setSubject(subject);
            formula.setTeacher(teacher);

            session.persist(formula);
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Formula addFormula(Formula formula, long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Subject subject = session.get(Subject.class, subjectId);
            Teacher teacher = session.get(Teacher.class, teacherId);
            Term term = session.get(Term.class, termId);
            String hql = "from Formula where subject.id = '" + subjectId + "' and teacher.id = '" + teacherId +
                    "' and term.id = '" + termId + "'";

            if (teacherId == 0)
                throw new GradingFactorException("Query param : teacherId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (termId == 0)
                throw new GradingFactorException("Query param : termId is required");
            if (formula == null)
                throw new GradingFactorDaoException("You tried to add formula with a null value");
            if (subject == null)
                throw new GradingFactorDaoException("Factor's subject with id : " + subjectId + " does not exist");
            if (teacher == null)
                throw new GradingFactorDaoException("Factor's teacher with id : " + teacherId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Factor's term with id : " + termId + " does not exist");
            if (session.createQuery(hql).list().size() > 0)
                throw new GradingFactorDaoException("The subject, teacher and term' combination is already exist");

            formula.setSubject(subject);
            formula.setTeacher(teacher);
            formula.setTerm(term);

            session.persist(formula);
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Formula updateFormulaById(long id, Formula newFormula, long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Formula formula = session.get(Formula.class, id);
            Subject subject = session.get(Subject.class, subjectId);
            Teacher teacher = session.get(Teacher.class, teacherId);
            Term term = session.get(Term.class, termId);

            if (newFormula == null)
                newFormula = new Formula();
            if (formula == null)
                throw new GradingFactorDaoException("Formula with id : " + id + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorDaoException("Factor's subject with id : " + subjectId + " does not exist");
            if (teacher == null && teacherId != 0)
                throw new GradingFactorDaoException("Factor's teacher with id : " + teacherId + " does not exist");
            if (term == null && termId != 0)
                throw new GradingFactorDaoException("Factor's term with id : " + termId + " does not exist");

            formula.setActivityPercentage(newFormula.getActivityPercentage());
            formula.setAssignmentPercentage(newFormula.getAssignmentPercentage());
            formula.setAttendancePercentage(newFormula.getAttendancePercentage());
            formula.setExamPercentage(newFormula.getExamPercentage());
            formula.setProjectPercentage(newFormula.getProjectPercentage());
            formula.setQuizPercentage(newFormula.getQuizPercentage());
            formula.setRecitationPercentage(newFormula.getRecitationPercentage());

            if (subjectId > 0) {
                if (subjectId == (formula.getSubject() != null ? formula.getSubject().getId() : 0))
                    throw new GradingFactorException("Formula's  subject with id : " + subjectId + " already exist");
                formula.setSubject(subject);
            }
            if (teacherId > 0) {
                if (teacherId == (formula.getTeacher() != null ? formula.getTeacher().getId() : 0))
                    throw new GradingFactorException("Formula's  teacher with id : " + teacherId + " already exist");
                formula.setTeacher(teacher);
            }
            if (termId > 0) {
                if (termId == (formula.getTerm() != null ? formula.getTerm().getId() : 0))
                    throw new GradingFactorException("Formula's  term with id : " + termId + " already exist");
                formula.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Formula deleteFormulaById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Formula formula = session.get(Formula.class, id);
            if (formula == null)
                throw new GradingFactorDaoException("Formula with id : " + id + " does not exist");
            session.delete(formula);
            session.getTransaction().commit();
            session.close();
            return formula;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
