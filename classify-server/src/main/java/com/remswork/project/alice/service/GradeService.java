package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Grade;

import java.util.List;

public interface GradeService {

    Grade getGradeById(long id) throws GradingFactorException;

    List<Grade> getGradeList() throws GradingFactorException;

    List<Grade> getGradeListByStudentId(long studentId) throws GradingFactorException;

    List<Grade> getGradeListByStudentId(long studentId, long termId) throws GradingFactorException;

    List<Grade> getGradeListByClass(long classId, long studentId) throws GradingFactorException;

    List<Grade> getGradeListByClass(long classId, long studentId, long termId) throws GradingFactorException;

    Grade addGrade(Grade grade, long classId, long studentId, long termId) throws GradingFactorException;

    Grade updateGradeById(long id, Grade newGrade) throws GradingFactorException;

    Grade updateGrade(long id, Grade newGrade, long classId, long studentId) throws GradingFactorException;

    Grade updateGrade(long id, Grade newGrade, long classId, long studentId, long termId)
            throws GradingFactorException;

    Grade deleteGradeById(long id) throws GradingFactorException;

}
