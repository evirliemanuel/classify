package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.QuizDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.QuizResult;
import com.remswork.project.alice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDaoImpl quizDao;


    @Override
    public Quiz getQuizById(long id) throws GradingFactorException {
        return quizDao.getQuizById(id);
    }

    @Override
    public List<Quiz> getQuizList() throws GradingFactorException {
        return quizDao.getQuizList();
    }

    @Override
    public List<Quiz> getQuizListByClassId(long classId) throws GradingFactorException {
        return quizDao.getQuizListByClassId(classId);
    }

    @Override
    public List<Quiz> getQuizListByClassId(long classId, long termId) throws GradingFactorException {
        return quizDao.getQuizListByClassId(classId, termId);
    }

    @Override
    public List<Quiz> getQuizListByStudentId(long studentId) throws GradingFactorException {
        return quizDao.getQuizListByStudentId(studentId);
    }

    @Override
    public List<Quiz> getQuizListByStudentId(long studentId, long termId) throws GradingFactorException {
        return quizDao.getQuizListByStudentId(studentId, termId);
    }

    @Override
    public QuizResult getQuizResultById(long id) throws GradingFactorException {
        return quizDao.getQuizResultById(id);
    }

    @Override
    public QuizResult getQuizResultByQuizAndStudentId(long quizId, long studentId) throws GradingFactorException {
        return quizDao.getQuizResultByQuizAndStudentId(quizId, studentId);
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long classId) throws GradingFactorException {
        return quizDao.addQuiz(quiz, classId);
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long classId, long termId) throws GradingFactorException {
        return quizDao.addQuiz(quiz, classId, termId);
    }

    @Override
    public QuizResult addQuizResult(int score, long quizId, long studentId) throws GradingFactorException {
        return quizDao.addQuizResult(score, quizId, studentId);
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long classId) throws GradingFactorException {
        return quizDao.updateQuizById(id, newQuiz, classId);
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long classId, long termId)
            throws GradingFactorException {
        return quizDao.updateQuizById(id, newQuiz, classId, termId);
    }

    @Override
    public QuizResult updateQuizResultByQuizAndStudentId(int score, long quizId, long studentId)
            throws GradingFactorException {
        return quizDao.updateQuizResultByQuizAndStudentId(score, quizId, studentId);
    }

    @Override
    public Quiz deleteQuizById(long id) throws GradingFactorException {
        return quizDao.deleteQuizById(id);
    }

    @Override
    public QuizResult deleteQuizResultByQuizAndStudentId(long quizId, long studentId)
            throws GradingFactorException {
        return quizDao.deleteQuizResultByQuizAndStudentId(quizId, studentId);
    }
}
