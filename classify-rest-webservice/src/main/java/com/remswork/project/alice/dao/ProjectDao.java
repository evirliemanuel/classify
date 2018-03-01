package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.ProjectResult;

import java.util.List;

public interface ProjectDao {

    Project getProjectById(long id) throws GradingFactorException;

    List<Project> getProjectList() throws GradingFactorException;

    List<Project> getProjectListByClassId(long classId) throws GradingFactorException;

    List<Project> getProjectListByClassId(long classId, long termId) throws GradingFactorException;

    List<Project> getProjectListByStudentId(long studentId) throws GradingFactorException;

    List<Project> getProjectListByStudentId(long studentId, long termId) throws GradingFactorException;

    ProjectResult getProjectResultById(long id) throws GradingFactorException;

    ProjectResult getProjectResultByProjectAndStudentId(long projectId, long studentId)
            throws GradingFactorException;

    Project addProject(Project project, long classId) throws GradingFactorException;

    Project addProject(Project project, long classId, long termId) throws GradingFactorException;

    ProjectResult addProjectResult(int score, long projectId, long studentId) throws GradingFactorException;

    Project updateProjectById(long id, Project newProject, long classId)
            throws GradingFactorException;

    Project updateProjectById(long id, Project newProject, long classId, long termId)
            throws GradingFactorException;

    ProjectResult updateProjectResultByProjectAndStudentId(int score, long projectId, long studentId)
            throws GradingFactorException;

    Project deleteProjectById(long projectId) throws GradingFactorException;

    ProjectResult deleteProjectResultByProjectAndStudentId(long projectId, long studentId)
            throws GradingFactorException;
}