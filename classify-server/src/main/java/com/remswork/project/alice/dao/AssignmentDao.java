package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.AssignmentResult;

import java.util.List;

public interface AssignmentDao {

    Assignment getAssignmentById(long id) throws GradingFactorException;

    List<Assignment> getAssignmentList() throws GradingFactorException;

    List<Assignment> getAssignmentListByClassId(long classId) throws GradingFactorException;

    List<Assignment> getAssignmentListByClassId(long classId, long termId) throws GradingFactorException;

    List<Assignment> getAssignmentListByStudentId(long studentId) throws GradingFactorException;

    List<Assignment> getAssignmentListByStudentId(long studentId, long termId) throws GradingFactorException;

    AssignmentResult getAssignmentResultById(long id) throws GradingFactorException;

    AssignmentResult getAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId)
            throws GradingFactorException;

    Assignment addAssignment(Assignment assignment, long classId) throws GradingFactorException;

    Assignment addAssignment(Assignment assignment, long classId, long termId) throws GradingFactorException;

    AssignmentResult addAssignmentResult(int score, long assignmentId, long studentId) throws GradingFactorException;

    Assignment updateAssignmentById(long id, Assignment newAssignment, long classId)
            throws GradingFactorException;

    Assignment updateAssignmentById(long id, Assignment newAssignment, long classId, long termId)
            throws GradingFactorException;

    AssignmentResult updateAssignmentResultByAssignmentAndStudentId(int score, long assignmentId, long studentId)
            throws GradingFactorException;

    Assignment deleteAssignmentById(long assignmentId) throws GradingFactorException;

    AssignmentResult deleteAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId)
            throws GradingFactorException;
}
