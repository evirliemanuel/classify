package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.TermDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Term;
import com.remswork.project.alice.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl implements TermService {

    @Autowired
    private TermDaoImpl termDao;

    @Override
    public Term getTermById(long id) throws GradingFactorException {
        return termDao.getTermById(id);
    }

    @Override
    public List<Term> getTermList() throws GradingFactorException {
        return termDao.getTermList();
    }

    @Override
    public Term addTerm(Term term) throws GradingFactorException {
        return termDao.addTerm(term);
    }

    @Override
    public Term updateTermById(long id, Term newTerm) throws GradingFactorException {
        return termDao.updateTermById(id, newTerm);
    }

    @Override
    public Term deleteTermById(long id) throws GradingFactorException {
        return termDao.deleteTermById(id);
    }
}
