package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.TermDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TermDaoImpl implements TermDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Term getTermById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Term term = session.get(Term.class, id);
            if(term == null)
                throw new GradingFactorDaoException("Term with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return term;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Term> getTermList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Term> termList = new ArrayList<>();
            Query query = session.createQuery("from Term");
            for (Object objTerm : query.list())
                termList.add((Term) objTerm);
            session.getTransaction().commit();
            session.close();
            return termList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Term addTerm(Term term) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (term == null)
                throw new GradingFactorException("You tried to add term with a null value");
            if (term.getName() == null)
                throw new GradingFactorException("Term's name is required");
            if (term.getName().trim().equals(""))
                throw new GradingFactorException("Term can't have an empty name");
            if (term.getCode() == 0)
                throw new GradingFactorException("Term's code is required");

            session.save(term);
            session.getTransaction().commit();
            session.close();
            return term;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Term updateTermById(long id, Term newTerm) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Term term = session.get(Term.class, id);

            if (term == null)
                throw new GradingFactorException("You tried to add term with a null value");
            if(newTerm == null)
                throw new GradingFactorException("You tried to update term with a null value");
            if(!(newTerm.getName() != null ? newTerm.getName() : "").trim().isEmpty())
                term.setName(newTerm.getName());
            if (newTerm.getCode() != 0)
                term.setCode(newTerm.getCode());

            session.getTransaction().commit();
            session.close();
            return term;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Term deleteTermById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Term term = session.get(Term.class, id);
            String[] table = new String[8];
            table[0] = Activity.class.getSimpleName();
            table[1] = Assignment.class.getSimpleName();
            table[2] = Attendance.class.getSimpleName();
            table[3] = Exam.class.getSimpleName();
            table[4] = Project.class.getSimpleName();
            table[5] = Quiz.class.getSimpleName();
            table[6] = Recitation.class.getSimpleName();
            table[7] = Grade.class.getSimpleName();

            if(term == null)
                throw new GradingFactorDaoException("Term with id : " + id + " does not exist");
            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where term.id = :termId");
                Query query = session.createQuery(hql);
                query.setParameter("termId", id);
                query.executeUpdate();
            }
            session.delete(term);
            session.getTransaction().commit();
            session.close();
            return term;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
