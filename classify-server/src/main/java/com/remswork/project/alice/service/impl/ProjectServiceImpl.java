package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ProjectDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.ProjectResult;
import com.remswork.project.alice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDaoImpl projectDao;


    @Override
    public Project getProjectById(long id) throws GradingFactorException {
        return projectDao.getProjectById(id);
    }

    @Override
    public List<Project> getProjectList() throws GradingFactorException {
        return projectDao.getProjectList();
    }

    @Override
    public List<Project> getProjectListByClassId(long classId) throws GradingFactorException {
        return projectDao.getProjectListByClassId(classId);
    }

    @Override
    public List<Project> getProjectListByClassId(long classId, long termId) throws GradingFactorException {
        return projectDao.getProjectListByClassId(classId, termId);
    }

    @Override
    public List<Project> getProjectListByStudentId(long studentId) throws GradingFactorException {
        return projectDao.getProjectListByStudentId(studentId);
    }

    @Override
    public List<Project> getProjectListByStudentId(long studentId, long termId) throws GradingFactorException {
        return projectDao.getProjectListByStudentId(studentId, termId);
    }

    @Override
    public ProjectResult getProjectResultById(long id) throws GradingFactorException {
        return projectDao.getProjectResultById(id);
    }

    @Override
    public ProjectResult getProjectResultByProjectAndStudentId(long projectId, long studentId) throws GradingFactorException {
        return projectDao.getProjectResultByProjectAndStudentId(projectId, studentId);
    }

    @Override
    public Project addProject(Project project, long classId) throws GradingFactorException {
        return projectDao.addProject(project, classId);
    }

    @Override
    public Project addProject(Project project, long classId, long termId) throws GradingFactorException {
        return projectDao.addProject(project, classId, termId);
    }

    @Override
    public ProjectResult addProjectResult(int score, long projectId, long studentId) throws GradingFactorException {
        return projectDao.addProjectResult(score, projectId, studentId);
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long classId) throws GradingFactorException {
        return projectDao.updateProjectById(id, newProject, classId);
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long classId, long termId)
            throws GradingFactorException {
        return projectDao.updateProjectById(id, newProject, classId, termId);
    }

    @Override
    public ProjectResult updateProjectResultByProjectAndStudentId(int score, long projectId, long studentId)
            throws GradingFactorException {
        return projectDao.updateProjectResultByProjectAndStudentId(score, projectId, studentId);
    }

    @Override
    public Project deleteProjectById(long id) throws GradingFactorException {
        return projectDao.deleteProjectById(id);
    }

    @Override
    public ProjectResult deleteProjectResultByProjectAndStudentId(long projectId, long studentId)
            throws GradingFactorException {
        return projectDao.deleteProjectResultByProjectAndStudentId(projectId, studentId);
    }
}
