package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
import com.remswork.project.alice.model.RecitationResult;

import java.util.List;

public interface RecitationDao {

    Recitation getRecitationById(long id) throws GradingFactorException;

    List<Recitation> getRecitationList() throws GradingFactorException;

    List<Recitation> getRecitationListByClassId(long classId) throws GradingFactorException;

    List<Recitation> getRecitationListByClassId(long classId, long termId) throws GradingFactorException;

    List<Recitation> getRecitationListByStudentId(long studentId) throws GradingFactorException;

    List<Recitation> getRecitationListByStudentId(long studentId, long termId) throws GradingFactorException;

    RecitationResult getRecitationResultById(long id) throws GradingFactorException;

    RecitationResult getRecitationResultByRecitationAndStudentId(long recitationId, long studentId)
            throws GradingFactorException;

    Recitation addRecitation(Recitation recitation, long classId) throws GradingFactorException;

    Recitation addRecitation(Recitation recitation, long classId, long termId) throws GradingFactorException;

    RecitationResult addRecitationResult(int score, long recitationId, long studentId) throws GradingFactorException;

    Recitation updateRecitationById(long id, Recitation newRecitation, long classId)
            throws GradingFactorException;

    Recitation updateRecitationById(long id, Recitation newRecitation, long classId, long termId)
            throws GradingFactorException;

    RecitationResult updateRecitationResultByRecitationAndStudentId(int score, long recitationId, long studentId)
            throws GradingFactorException;

    Recitation deleteRecitationById(long recitationId) throws GradingFactorException;

    RecitationResult deleteRecitationResultByRecitationAndStudentId(long recitationId, long studentId)
            throws GradingFactorException;
}
