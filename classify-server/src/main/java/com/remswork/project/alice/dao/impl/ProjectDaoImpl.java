package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ActivityDao;
import com.remswork.project.alice.dao.ProjectDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.model.Class;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Project getProjectById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();
            Query query = session.createQuery("from Project");
            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();

            Query query = session.createQuery("from Project where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();
            String hql = "from Project where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Project> projectList = new ArrayList<>();
            String hql = "from ProjectResult as R join R.project where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                projectList.add((Project) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Project> projectList = new ArrayList<>();
            String hql = "from ProjectResult as R join R.project as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Project's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                projectList.add((Project) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ProjectResult getProjectResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            ProjectResult result = session.get(ProjectResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("ProjectResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ProjectResult getProjectResultByProjectAndStudentId(long projectId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, projectId);
            Student student = session.get(Student.class, studentId);
            String hql = "from ProjectResult as R where R.project.id = :projectId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("projectId", projectId);
            query.setParameter("studentId", studentId);

            if (project == null)
                throw new GradingFactorDaoException("Project with id : " + projectId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No ProjectResult found. Try use query param " +
                        "(ex. studentId=[id])");

            ProjectResult result = (ProjectResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project addProject(Project project, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (project == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Project's class with id : " + classId + " does not exist");
            if (project.getTitle() == null)
                throw new GradingFactorDaoException("Project's title is required");
            if (project.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Project can't have an empty title");
            if (project.getDate() == null)
                throw new GradingFactorDaoException("Project's date is required");
            if (project.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Project can't have an empty date");
            if(project.getItemTotal() < 0)
                throw new GradingFactorDaoException("Project's itemTotal is invalid");

            project.set_class(_class);
            session.persist(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project addProject(Project project, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (project == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Project's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Project's term with id : " + termId + " does not exist");
            if (project.getTitle() == null)
                throw new GradingFactorDaoException("Project's title is required");
            if (project.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Project can't have an empty title");
            if (project.getDate() == null)
                throw new GradingFactorDaoException("Project's date is required");
            if (project.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Project can't have an empty date");
            if(project.getItemTotal() < 0)
                throw new GradingFactorDaoException("Project's itemTotal is invalid");

            project.set_class(_class);
            project.setTerm(term);

            session.persist(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ProjectResult addProjectResult(int score, long projectId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, projectId);
            Student student = session.get(Student.class, studentId);
            ProjectResult result = new ProjectResult();
            String hql = "from ProjectResult as R where R.project.id = :projectId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("projectId", projectId);
            query.setParameter("studentId", studentId);

            if (project == null)
                throw new GradingFactorDaoException("Project's project with id : " + projectId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");
            if (projectId < 1)
                throw new GradingFactorDaoException("Query param : projectId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > project.getItemTotal())
                throw new GradingFactorDaoException("ProjectResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("ProjectResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setProject(project);
            result.setStudent(student);

            session.persist(result);
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            Class _class = session.get(Class.class, classId);

            if(newProject == null)
                newProject = new Project();
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Project's class with id : " + classId + " does not exist");
            if(!(newProject.getTitle() != null ? newProject.getTitle() : "").trim().isEmpty())
                project.setTitle(newProject.getTitle());
            if(!(newProject.getDate() != null ? newProject.getDate() : "").trim().isEmpty())
                project.setDate(newProject.getDate());
            if(classId > 0) {
                if(classId == (project.get_class() != null ? project.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Project's  class with id : " + classId + " already exist");
                project.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return project;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newProject == null)
                newProject = new Project();
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Project's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Project's term with id : " + termId + " does not exist");
            if(!(newProject.getTitle() != null ? newProject.getTitle() : "").trim().isEmpty())
                project.setTitle(newProject.getTitle());
            if(!(newProject.getDate() != null ? newProject.getDate() : "").trim().isEmpty())
                project.setDate(newProject.getDate());
            if(classId > 0) {
                if(classId == (project.get_class() != null ? project.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Project's  class with id : " + classId + " already exist");
                project.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (project.getTerm() != null ? project.getTerm().getId() : 0))
                    project.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return project;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ProjectResult updateProjectResultByProjectAndStudentId(int score, long projectId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, projectId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ProjectResult as R where R.project.id = :projectId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("projectId", projectId);
            query.setParameter("studentId", studentId);

            if (project == null)
                throw new GradingFactorDaoException("Project's project with id : " + projectId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");
            if (projectId < 1)
                throw new GradingFactorDaoException("Query param : projectId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            ProjectResult result = (ProjectResult) query.list().get(0);

            result.setScore(score);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project deleteProjectById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = ProjectResult.class.getSimpleName();

            Project project = session.get(Project.class, id);
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where project.id = :projectId");
                Query query = session.createQuery(hql);
                query.setParameter("projectId", id);
                query.executeUpdate();
            }
            session.delete(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public ProjectResult deleteProjectResultByProjectAndStudentId(long projectId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, projectId);
            Student student = session.get(Student.class, studentId);

            String hql = "from ProjectResult as R where R.project.id = :projectId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("projectId", projectId);
            query.setParameter("studentId", studentId);

            if (project == null)
                throw new GradingFactorDaoException("Project's project with id : " + projectId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Project's student with id : " + studentId + " does not exist");
            if (projectId < 1)
                throw new GradingFactorDaoException("Query param : projectId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            ProjectResult result = (ProjectResult) query.list().get(0);

            session.delete(result);
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}