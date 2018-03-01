package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.RecitationDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
import com.remswork.project.alice.model.RecitationResult;
import com.remswork.project.alice.service.RecitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecitationServiceImpl implements RecitationService {

    @Autowired
    private RecitationDaoImpl recitationDao;


    @Override
    public Recitation getRecitationById(long id) throws GradingFactorException {
        return recitationDao.getRecitationById(id);
    }

    @Override
    public List<Recitation> getRecitationList() throws GradingFactorException {
        return recitationDao.getRecitationList();
    }

    @Override
    public List<Recitation> getRecitationListByClassId(long classId) throws GradingFactorException {
        return recitationDao.getRecitationListByClassId(classId);
    }

    @Override
    public List<Recitation> getRecitationListByClassId(long classId, long termId) throws GradingFactorException {
        return recitationDao.getRecitationListByClassId(classId, termId);
    }

    @Override
    public List<Recitation> getRecitationListByStudentId(long studentId) throws GradingFactorException {
        return recitationDao.getRecitationListByStudentId(studentId);
    }

    @Override
    public List<Recitation> getRecitationListByStudentId(long studentId, long termId) throws GradingFactorException {
        return recitationDao.getRecitationListByStudentId(studentId, termId);
    }

    @Override
    public RecitationResult getRecitationResultById(long id) throws GradingFactorException {
        return recitationDao.getRecitationResultById(id);
    }

    @Override
    public RecitationResult getRecitationResultByRecitationAndStudentId(long recitationId, long studentId) throws GradingFactorException {
        return recitationDao.getRecitationResultByRecitationAndStudentId(recitationId, studentId);
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long classId) throws GradingFactorException {
        return recitationDao.addRecitation(recitation, classId);
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long classId, long termId) throws GradingFactorException {
        return recitationDao.addRecitation(recitation, classId, termId);
    }

    @Override
    public RecitationResult addRecitationResult(int score, long recitationId, long studentId) throws GradingFactorException {
        return recitationDao.addRecitationResult(score, recitationId, studentId);
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long classId) throws GradingFactorException {
        return recitationDao.updateRecitationById(id, newRecitation, classId);
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long classId, long termId)
            throws GradingFactorException {
        return recitationDao.updateRecitationById(id, newRecitation, classId, termId);
    }

    @Override
    public RecitationResult updateRecitationResultByRecitationAndStudentId(int score, long recitationId, long studentId)
            throws GradingFactorException {
        return recitationDao.updateRecitationResultByRecitationAndStudentId(score, recitationId, studentId);
    }

    @Override
    public Recitation deleteRecitationById(long id) throws GradingFactorException {
        return recitationDao.deleteRecitationById(id);
    }

    @Override
    public RecitationResult deleteRecitationResultByRecitationAndStudentId(long recitationId, long studentId)
            throws GradingFactorException {
        return recitationDao.deleteRecitationResultByRecitationAndStudentId(recitationId, studentId);
    }
}
