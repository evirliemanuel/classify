package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Term;

import java.util.List;

public interface TermDao {

    Term getTermById(long id) throws GradingFactorException;

    List<Term> getTermList() throws GradingFactorException;

    Term addTerm(Term term) throws GradingFactorException;

    Term updateTermById(long id, Term newTerm) throws GradingFactorException;

    Term deleteTermById(long id) throws GradingFactorException;
}
