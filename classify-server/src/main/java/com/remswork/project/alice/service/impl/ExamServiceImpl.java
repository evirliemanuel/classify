package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ExamDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.ExamResult;
import com.remswork.project.alice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDaoImpl examDao;


    @Override
    public Exam getExamById(long id) throws GradingFactorException {
        return examDao.getExamById(id);
    }

    @Override
    public List<Exam> getExamList() throws GradingFactorException {
        return examDao.getExamList();
    }

    @Override
    public List<Exam> getExamListByClassId(long classId) throws GradingFactorException {
        return examDao.getExamListByClassId(classId);
    }

    @Override
    public List<Exam> getExamListByClassId(long classId, long termId) throws GradingFactorException {
        return examDao.getExamListByClassId(classId, termId);
    }

    @Override
    public List<Exam> getExamListByStudentId(long studentId) throws GradingFactorException {
        return examDao.getExamListByStudentId(studentId);
    }

    @Override
    public List<Exam> getExamListByStudentId(long studentId, long termId) throws GradingFactorException {
        return examDao.getExamListByStudentId(studentId, termId);
    }

    @Override
    public ExamResult getExamResultById(long id) throws GradingFactorException {
        return examDao.getExamResultById(id);
    }

    @Override
    public ExamResult getExamResultByExamAndStudentId(long examId, long studentId) throws GradingFactorException {
        return examDao.getExamResultByExamAndStudentId(examId, studentId);
    }

    @Override
    public Exam addExam(Exam exam, long classId) throws GradingFactorException {
        return examDao.addExam(exam, classId);
    }

    @Override
    public Exam addExam(Exam exam, long classId, long termId) throws GradingFactorException {
        return examDao.addExam(exam, classId, termId);
    }

    @Override
    public ExamResult addExamResult(int score, long examId, long studentId) throws GradingFactorException {
        return examDao.addExamResult(score, examId, studentId);
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long classId) throws GradingFactorException {
        return examDao.updateExamById(id, newExam, classId);
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long classId, long termId)
            throws GradingFactorException {
        return examDao.updateExamById(id, newExam, classId, termId);
    }

    @Override
    public ExamResult updateExamResultByExamAndStudentId(int score, long examId, long studentId)
            throws GradingFactorException {
        return examDao.updateExamResultByExamAndStudentId(score, examId, studentId);
    }

    @Override
    public Exam deleteExamById(long id) throws GradingFactorException {
        return examDao.deleteExamById(id);
    }

    @Override
    public ExamResult deleteExamResultByExamAndStudentId(long examId, long studentId)
            throws GradingFactorException {
        return examDao.deleteExamResultByExamAndStudentId(examId, studentId);
    }
}
