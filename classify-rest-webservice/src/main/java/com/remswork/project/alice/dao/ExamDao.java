package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.ExamResult;

import java.util.List;

public interface ExamDao {

    Exam getExamById(long id) throws GradingFactorException;

    List<Exam> getExamList() throws GradingFactorException;

    List<Exam> getExamListByClassId(long classId) throws GradingFactorException;

    List<Exam> getExamListByClassId(long classId, long termId) throws GradingFactorException;

    List<Exam> getExamListByStudentId(long studentId) throws GradingFactorException;

    List<Exam> getExamListByStudentId(long studentId, long termId) throws GradingFactorException;

    ExamResult getExamResultById(long id) throws GradingFactorException;

    ExamResult getExamResultByExamAndStudentId(long examId, long studentId)
            throws GradingFactorException;

    Exam addExam(Exam exam, long classId) throws GradingFactorException;

    Exam addExam(Exam exam, long classId, long termId) throws GradingFactorException;

    ExamResult addExamResult(int score, long examId, long studentId) throws GradingFactorException;

    Exam updateExamById(long id, Exam newExam, long classId)
            throws GradingFactorException;

    Exam updateExamById(long id, Exam newExam, long classId, long termId)
            throws GradingFactorException;

    ExamResult updateExamResultByExamAndStudentId(int score, long examId, long studentId)
            throws GradingFactorException;

    Exam deleteExamById(long examId) throws GradingFactorException;

    ExamResult deleteExamResultByExamAndStudentId(long examId, long studentId)
            throws GradingFactorException;
}
