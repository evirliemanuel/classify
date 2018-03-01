package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.GradeDao;
import com.remswork.project.alice.dao.exception.DepartmentDaoException;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Term;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GradeDaoImpl implements GradeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Grade getGradeById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            session.getTransaction().commit();
            session.close();
            return  grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            Query query = session.createQuery("from Grade");

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.student.id = :studentId and G.term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G._class.id = :classId and G.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("studentId", studentId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G._class.id = :classId and G.student.id = :studentId " +
                    "and G.term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade addGrade(Grade grade, long classId, long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);

            if(grade == null)
                throw new GradingFactorDaoException("You tried to add grade with a null value");
            if(grade.getTotalScore() < 0)
                throw new GradingFactorDaoException("Grade's total score is not valid");
            if(grade.getActivityScore() < 0)
                throw new GradingFactorDaoException("Grade's activity score is not valid");
            if(grade.getAssignmentScore() < 0)
                throw new GradingFactorDaoException("Grade's assignment score is not valid");
            if(grade.getAttendanceScore() < 0)
                throw new GradingFactorDaoException("Grade's attendance score is not valid");
            if(grade.getExamScore() < 0)
                throw new GradingFactorDaoException("Grade's exam score is not valid");
            if(grade.getProjectScore() < 0)
                throw new GradingFactorDaoException("Grade's project score is not valid");
            if(grade.getQuizScore() < 0)
                throw new GradingFactorDaoException("Grade's quiz score is not valid");
            if(classId < 1)
                throw new GradingFactorDaoException("Query param : classId is required.");
            if(studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required.");
            if(termId < 1)
                throw new GradingFactorDaoException("Query param : termId is required.");
            if(_class == null)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(term == null)
                throw new GradingFactorDaoException("Term with id : " + termId + " doesn't exist.");

            grade.set_class(_class);
            grade.setStudent(student);
            grade.setTerm(term);

            session.persist(grade);
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGradeById(long id, Grade newGrade) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(newGrade.getTotalScore() >= 0 && grade.getTotalScore() > 0)
                grade.setTotalScore(newGrade.getTotalScore());
            if(newGrade.getActivityScore() > 0 && grade.getActivityScore() > 0)
                grade.setActivityScore(newGrade.getActivityScore());
            if(newGrade.getAssignmentScore() > 0 && grade.getAssignmentScore() > 0)
                grade.setAssignmentScore(newGrade.getAssignmentScore());
            if(newGrade.getAttendanceScore() > 0 && grade.getAttendanceScore() > 0)
                grade.setAttendanceScore(newGrade.getAttendanceScore());
            if(newGrade.getExamScore() > 0 && grade.getExamScore() > 0)
                grade.setExamScore(newGrade.getExamScore());
            if(newGrade.getProjectScore() > 0 && grade.getProjectScore() > 0)
                grade.setProjectScore(newGrade.getProjectScore());
            if(newGrade.getQuizScore() > 0 && grade.getQuizScore() > 0)
                grade.setQuizScore(newGrade.getQuizScore());
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGrade(long id, Grade newGrade, long classId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);

            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(_class == null && classId != 0)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null && studentId != 0)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(classId < 0)
                throw new GradingFactorDaoException("The class id is invalid");
            if(studentId < 0)
                throw new GradingFactorDaoException("The student id is invalid");
            if(newGrade.getTotalScore() >= 0 && grade.getTotalScore() > 0)
                grade.setTotalScore(newGrade.getTotalScore());
            if(newGrade.getActivityScore() > 0 && grade.getActivityScore() > 0)
                grade.setActivityScore(newGrade.getActivityScore());
            if(newGrade.getAssignmentScore() > 0 && grade.getAssignmentScore() > 0)
                grade.setAssignmentScore(newGrade.getAssignmentScore());
            if(newGrade.getAttendanceScore() > 0 && grade.getAttendanceScore() > 0)
                grade.setAttendanceScore(newGrade.getAttendanceScore());
            if(newGrade.getExamScore() > 0 && grade.getExamScore() > 0)
                grade.setExamScore(newGrade.getExamScore());
            if(newGrade.getProjectScore() > 0 && grade.getProjectScore() > 0)
                grade.setProjectScore(newGrade.getProjectScore());
            if(newGrade.getQuizScore() > 0 && grade.getQuizScore() > 0)
                grade.setQuizScore(newGrade.getQuizScore());
            if(classId > 0) {
                if((grade.get_class() != null ? grade.get_class().getId() : 0) == classId)
                    throw new GradingFactorDaoException("The class id is already exist");
                grade.set_class(_class);
            }
            if(studentId > 0) {
                if((grade.getStudent() != null ? grade.getStudent().getId() : 0) == studentId)
                    throw new GradingFactorDaoException("The student id is already exist");
                grade.setStudent(student);
            }
            grade.set_class(_class);
            grade.setStudent(student);
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGrade(long id, Grade newGrade, long classId, long studentId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);

            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(_class == null && classId != 0)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null && studentId != 0)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(term == null && termId != 0)
                throw new GradingFactorDaoException("Term with id : " + termId + " doesn't exist.");
            if(classId < 0)
                throw new GradingFactorDaoException("The class id is invalid");
            if(studentId < 0)
                throw new GradingFactorDaoException("The student id is invalid");
            if(termId < 0)
                throw new GradingFactorDaoException("The term id is invalid");
            if(newGrade.getTotalScore() >= 0 && grade.getTotalScore() > 0)
                grade.setTotalScore(newGrade.getTotalScore());
            if(newGrade.getActivityScore() > 0 && grade.getActivityScore() > 0)
                grade.setActivityScore(newGrade.getActivityScore());
            if(newGrade.getAssignmentScore() > 0 && grade.getAssignmentScore() > 0)
                grade.setAssignmentScore(newGrade.getAssignmentScore());
            if(newGrade.getAttendanceScore() > 0 && grade.getAttendanceScore() > 0)
                grade.setAttendanceScore(newGrade.getAttendanceScore());
            if(newGrade.getExamScore() > 0 && grade.getExamScore() > 0)
                grade.setExamScore(newGrade.getExamScore());
            if(newGrade.getProjectScore() > 0 && grade.getProjectScore() > 0)
                grade.setProjectScore(newGrade.getProjectScore());
            if(newGrade.getQuizScore() > 0 && grade.getQuizScore() > 0)
                grade.setQuizScore(newGrade.getQuizScore());
            if(classId > 0) {
                if((grade.get_class() != null ? grade.get_class().getId() : 0) == classId)
                    throw new GradingFactorDaoException("The class id is already exist");
                grade.set_class(_class);
            }
            if(studentId > 0) {
                if((grade.getStudent() != null ? grade.getStudent().getId() : 0) == studentId)
                    throw new GradingFactorDaoException("The student id is already exist");
                grade.setStudent(student);
            }
            if(termId > 0) {
                if((grade.getTerm() != null ? grade.getTerm().getId() : 0) == termId)
                    throw new GradingFactorDaoException("The term id is already exist");
                grade.setTerm(term);
            }

            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade deleteGradeById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            session.delete(grade);
            session.getTransaction().commit();
            session.close();
            return  grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
