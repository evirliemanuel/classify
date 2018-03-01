package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.QuizResult;

import java.util.List;

public interface QuizService {

    Quiz getQuizById(long id) throws GradingFactorException;

    List<Quiz> getQuizList() throws GradingFactorException;

    List<Quiz> getQuizListByClassId(long classId) throws GradingFactorException;

    List<Quiz> getQuizListByClassId(long classId, long termId) throws GradingFactorException;

    List<Quiz> getQuizListByStudentId(long studentId) throws GradingFactorException;

    List<Quiz> getQuizListByStudentId(long studentId, long termId) throws GradingFactorException;

    QuizResult getQuizResultById(long id) throws GradingFactorException;

    QuizResult getQuizResultByQuizAndStudentId(long quizId, long studentId)
            throws GradingFactorException;

    Quiz addQuiz(Quiz quiz, long classId) throws GradingFactorException;

    Quiz addQuiz(Quiz quiz, long classId, long termId) throws GradingFactorException;

    QuizResult addQuizResult(int score, long quizId, long studentId) throws GradingFactorException;

    Quiz updateQuizById(long id, Quiz newQuiz, long classId)
            throws GradingFactorException;

    Quiz updateQuizById(long id, Quiz newQuiz, long classId, long termId)
            throws GradingFactorException;

    QuizResult updateQuizResultByQuizAndStudentId(int score, long quizId, long studentId)
            throws GradingFactorException;

    Quiz deleteQuizById(long quizId) throws GradingFactorException;

    QuizResult deleteQuizResultByQuizAndStudentId(long quizId, long studentId)
            throws GradingFactorException;
}
