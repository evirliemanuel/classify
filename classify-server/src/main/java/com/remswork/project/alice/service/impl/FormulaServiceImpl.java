package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.FormulaDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormulaServiceImpl implements FormulaService {

    @Autowired
    private FormulaDaoImpl formulaDao;

    @Override
    public Formula getFormulaById(long id) throws GradingFactorException {
        return formulaDao.getFormulaById(id);
    }

    @Override
    public Formula getFormulaBySubjectAndTeacherId(long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        return formulaDao.getFormulaBySubjectAndTeacherId(subjectId, teacherId, termId);
    }

    @Override
    public List<Formula> getFormulaList() throws GradingFactorException {
        return formulaDao.getFormulaList();
    }

    @Override
    public List<Formula> getFormulaListByTeacherId(long teacherId) throws GradingFactorException {
        return formulaDao.getFormulaListByTeacherId(teacherId);
    }

    @Override
    public Formula addFormula(Formula formula, long subjectId, long teacherId) throws GradingFactorException {
        return formulaDao.addFormula(formula, subjectId, teacherId);
    }

    @Override
    public Formula addFormula(Formula formula, long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        return formulaDao.addFormula(formula, subjectId, teacherId, termId);
    }

    @Override
    public Formula updateFormulaById(long id, Formula newFormula, long subjectId, long teacherId, long termId)
            throws GradingFactorException {
        return formulaDao.updateFormulaById(id, newFormula, subjectId, teacherId, termId);
    }

    @Override
    public Formula deleteFormulaById(long id) throws GradingFactorException {
        return formulaDao.deleteFormulaById(id);
    }
}
