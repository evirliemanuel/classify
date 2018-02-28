package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.GradeDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    @Autowired
    private GradeDaoImpl gradeDao;

    @Override
    public Grade getGradeById(long id) throws GradingFactorException {
        return gradeDao.getGradeById(id);
    }

    @Override
    public List<Grade> getGradeList() throws GradingFactorException {
        return gradeDao.getGradeList();
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId) throws GradingFactorException {
        return gradeDao.getGradeListByStudentId(studentId);
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId, long termId) throws GradingFactorException {
        return gradeDao.getGradeListByStudentId(studentId, termId);
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId) throws GradingFactorException {
        return gradeDao.getGradeListByClass(classId, studentId);
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId, long termId) throws GradingFactorException {
        return gradeDao.getGradeListByClass(classId, studentId, termId);
    }

    @Override
    public Grade addGrade(Grade grade, long classId, long studentId, long termId) throws GradingFactorException {
        return gradeDao.addGrade(grade, classId, studentId, termId);
    }

    @Override
    public Grade updateGradeById(long id, Grade newGrade) throws GradingFactorException {
        return gradeDao.updateGradeById(id, newGrade);
    }

    @Override
    public Grade updateGrade(long id, Grade newGrade, long classId, long studentId) throws GradingFactorException {
        return gradeDao.updateGrade(id, newGrade, classId, studentId);
    }

    @Override
    public Grade updateGrade(long id, Grade newGrade, long classId, long studentId, long termId)
            throws GradingFactorException {
        return gradeDao.updateGrade(id, newGrade, classId, studentId, termId);
    }

    @Override
    public Grade deleteGradeById(long id) throws GradingFactorException {
        return gradeDao.deleteGradeById(id);
    }
}
