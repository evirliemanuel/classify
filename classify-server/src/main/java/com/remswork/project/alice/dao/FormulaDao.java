package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;

import java.util.List;

public interface FormulaDao {

    Formula getFormulaById(long id) throws GradingFactorException;
    Formula getFormulaBySubjectAndTeacherId(long subjectId, long teacherId, long termId) throws GradingFactorException;
    List<Formula> getFormulaList() throws GradingFactorException;
    List<Formula> getFormulaListByTeacherId(long teacherId) throws GradingFactorException;
    Formula addFormula(Formula formula, long subjectId, long teacherId) throws GradingFactorException;
    Formula addFormula(Formula formula, long subjectId, long teacherId, long termId) throws GradingFactorException;
    Formula updateFormulaById(long id, Formula newFormula, long subjectId, long teacherId, long termId)
            throws GradingFactorException;
    Formula deleteFormulaById(long id) throws GradingFactorException;
}
