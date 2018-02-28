package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.AssignmentDao;
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
public class AssignmentDaoImpl implements AssignmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Assignment getAssignmentById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();
            Query query = session.createQuery("from Assignment");
            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();

            Query query = session.createQuery("from Assignment where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();
            String hql = "from Assignment where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Assignment> assignmentList = new ArrayList<>();
            String hql = "from AssignmentResult as R join R.assignment where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                assignmentList.add((Assignment) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Assignment> assignmentList = new ArrayList<>();
            String hql = "from AssignmentResult as R join R.assignment as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Assignment's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                assignmentList.add((Assignment) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AssignmentResult getAssignmentResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            AssignmentResult result = session.get(AssignmentResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("AssignmentResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AssignmentResult getAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, assignmentId);
            Student student = session.get(Student.class, studentId);
            String hql = "from AssignmentResult as R where R.assignment.id = :assignmentId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("assignmentId", assignmentId);
            query.setParameter("studentId", studentId);

            if (assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + assignmentId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No AssignmentResult found. Try use query param " +
                        "(ex. studentId=[id])");

            AssignmentResult result = (AssignmentResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (assignment == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Assignment's class with id : " + classId + " does not exist");
            if (assignment.getTitle() == null)
                throw new GradingFactorDaoException("Assignment's title is required");
            if (assignment.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Assignment can't have an empty title");
            if (assignment.getDate() == null)
                throw new GradingFactorDaoException("Assignment's date is required");
            if (assignment.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Assignment can't have an empty date");
            if(assignment.getItemTotal() < 0)
                throw new GradingFactorDaoException("Assignment's itemTotal is invalid");

            assignment.set_class(_class);
            session.persist(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (assignment == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Assignment's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Assignment's term with id : " + termId + " does not exist");
            if (assignment.getTitle() == null)
                throw new GradingFactorDaoException("Assignment's title is required");
            if (assignment.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Assignment can't have an empty title");
            if (assignment.getDate() == null)
                throw new GradingFactorDaoException("Assignment's date is required");
            if (assignment.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Assignment can't have an empty date");
            if(assignment.getItemTotal() < 0)
                throw new GradingFactorDaoException("Assignment's itemTotal is invalid");

            assignment.set_class(_class);
            assignment.setTerm(term);

            session.persist(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AssignmentResult addAssignmentResult(int score, long assignmentId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, assignmentId);
            Student student = session.get(Student.class, studentId);
            AssignmentResult result = new AssignmentResult();
            String hql = "from AssignmentResult as R where R.assignment.id = :assignmentId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("assignmentId", assignmentId);
            query.setParameter("studentId", studentId);

            if (assignment == null)
                throw new GradingFactorDaoException("Assignment's assignment with id : " + assignmentId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");
            if (assignmentId < 1)
                throw new GradingFactorDaoException("Query param : assignmentId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(score < 0 && score > assignment.getItemTotal())
                throw new GradingFactorDaoException("AssignmentResult's score is invalid");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("AssignmentResult's  student with id : " + studentId +
                        " already exist");

            result.setScore(score);
            result.setAssignment(assignment);
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
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            Class _class = session.get(Class.class, classId);

            if(newAssignment == null)
                newAssignment = new Assignment();
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Assignment's class with id : " + classId + " does not exist");
            if(!(newAssignment.getTitle() != null ? newAssignment.getTitle() : "").trim().isEmpty())
                assignment.setTitle(newAssignment.getTitle());
            if(!(newAssignment.getDate() != null ? newAssignment.getDate() : "").trim().isEmpty())
                assignment.setDate(newAssignment.getDate());
            if(classId > 0) {
                if(classId == (assignment.get_class() != null ? assignment.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Assignment's  class with id : " + classId + " already exist");
                assignment.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return assignment;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newAssignment == null)
                newAssignment = new Assignment();
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Assignment's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Assignment's term with id : " + termId + " does not exist");
            if(!(newAssignment.getTitle() != null ? newAssignment.getTitle() : "").trim().isEmpty())
                assignment.setTitle(newAssignment.getTitle());
            if(!(newAssignment.getDate() != null ? newAssignment.getDate() : "").trim().isEmpty())
                assignment.setDate(newAssignment.getDate());
            if(classId > 0) {
                if(classId == (assignment.get_class() != null ? assignment.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Assignment's  class with id : " + classId + " already exist");
                assignment.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (assignment.getTerm() != null ? assignment.getTerm().getId() : 0))
                    assignment.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return assignment;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AssignmentResult updateAssignmentResultByAssignmentAndStudentId(int score, long assignmentId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, assignmentId);
            Student student = session.get(Student.class, studentId);

            String hql = "from AssignmentResult as R where R.assignment.id = :assignmentId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("assignmentId", assignmentId);
            query.setParameter("studentId", studentId);

            if (assignment == null)
                throw new GradingFactorDaoException("Assignment's assignment with id : " + assignmentId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");
            if (assignmentId < 1)
                throw new GradingFactorDaoException("Query param : assignmentId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            AssignmentResult result = (AssignmentResult) query.list().get(0);

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
    public Assignment deleteAssignmentById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = AssignmentResult.class.getSimpleName();

            Assignment assignment = session.get(Assignment.class, id);
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where assignment.id = :assignmentId");
                Query query = session.createQuery(hql);
                query.setParameter("assignmentId", id);
                query.executeUpdate();
            }
            session.delete(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AssignmentResult deleteAssignmentResultByAssignmentAndStudentId(long assignmentId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, assignmentId);
            Student student = session.get(Student.class, studentId);

            String hql = "from AssignmentResult as R where R.assignment.id = :assignmentId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("assignmentId", assignmentId);
            query.setParameter("studentId", studentId);

            if (assignment == null)
                throw new GradingFactorDaoException("Assignment's assignment with id : " + assignmentId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Assignment's student with id : " + studentId + " does not exist");
            if (assignmentId < 1)
                throw new GradingFactorDaoException("Query param : assignmentId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            AssignmentResult result = (AssignmentResult) query.list().get(0);

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
