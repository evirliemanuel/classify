package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.AssignmentDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.AssignmentResult;
import com.remswork.project.alice.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentDaoImpl assignmentDao;


    @Override
    public Assignment getAssignmentById(long id) throws GradingFactorException {
        return assignmentDao.getAssignmentById(id);
    }

    @Override
    public List<Assignment> getAssignmentList() throws GradingFactorException {
        return assignmentDao.getAssignmentList();
    }

    @Override
    public List<Assignment> getAssignmentListByClassId(long classId) throws GradingFactorException {
        return assignmentDao.getAssignmentListByClassId(classId);
    }

    @Override
    public List<Assignment> getAssignmentListByClassId(long classId, long termId) throws GradingFactorException {
        return assignmentDao.getAssignmentListByClassId(classId, termId);
    }

    @Override
    public List<Assignment> getAssignmentListByStudentId(long studentId) throws GradingFactorException {
        return assignmentDao.getAssignmentListByStudentId(studentId);
    }

    @Override
    public List<Assignment> getAssignmentListByStudentId(long studentId, long termId) throws GradingFactorException {
        return assignmentDao.getAssignmentListByStudentId(studentId, termId);
    }

    @Override
    public AssignmentResult getAssignmentResultById(long id) throws GradingFactorException {
        return assignmentDao.getAssignmentResultById(id);
    }

    @Override
    public AssignmentResult getAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId) throws GradingFactorException {
        return assignmentDao.getAssignmentResultByAssignmentAndStudentId(assignmentId, studentId);
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long classId) throws GradingFactorException {
        return assignmentDao.addAssignment(assignment, classId);
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long classId, long termId) throws GradingFactorException {
        return assignmentDao.addAssignment(assignment, classId, termId);
    }

    @Override
    public AssignmentResult addAssignmentResult(int score, long assignmentId, long studentId) throws GradingFactorException {
        return assignmentDao.addAssignmentResult(score, assignmentId, studentId);
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long classId) throws GradingFactorException {
        return assignmentDao.updateAssignmentById(id, newAssignment, classId);
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long classId, long termId)
            throws GradingFactorException {
        return assignmentDao.updateAssignmentById(id, newAssignment, classId, termId);
    }

    @Override
    public AssignmentResult updateAssignmentResultByAssignmentAndStudentId(int score, long assignmentId, long studentId)
            throws GradingFactorException {
        return assignmentDao.updateAssignmentResultByAssignmentAndStudentId(score, assignmentId, studentId);
    }

    @Override
    public Assignment deleteAssignmentById(long id) throws GradingFactorException {
        return assignmentDao.deleteAssignmentById(id);
    }

    @Override
    public AssignmentResult deleteAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId)
            throws GradingFactorException {
        return assignmentDao.deleteAssignmentResultByAssignmentAndStudentId(assignmentId, studentId);
    }
}