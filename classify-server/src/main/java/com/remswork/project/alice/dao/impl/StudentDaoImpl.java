package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.StudentDao;
import com.remswork.project.alice.dao.exception.StudentDaoException;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.exception.StudentException;
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
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private SectionDaoImpl sectionDao;

    @Override
    public Student getStudentById(long id) throws StudentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Student student = session.get(Student.class, id);
            if(student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException e){
            session.close();
            throw new StudentException(e.getMessage());
        }
    }

    @Override
    public List<Student> getStudentList() throws StudentException {
        List<Student> studentList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Query query = session.createQuery("from Student");
            for(Object studentObj : query.list())
                studentList.add((Student) studentObj);
            session.getTransaction().commit();
            session.close();
            return studentList;
        }catch (StudentDaoException e) {
            session.close();
            throw new StudentException(e.getMessage());
        }
    }

    @Override
    public Student addStudent(Student student, long sectionId) throws StudentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            if (student == null)
                throw new StudentDaoException("You tried to add student with a null value");
            if (student.getStudentNumber() == 0)
                throw new StudentDaoException("Student's studentnumber is required");
            if (student.getFirstName() == null)
                throw new StudentDaoException("Student's first name is required");
            if (student.getFirstName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty first name");
            if (student.getLastName() == null)
                throw new StudentDaoException("Student's last name is required");
            if (student.getLastName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty last name");
            if (student.getMiddleName() == null)
                throw new StudentDaoException("Student's middle name is required");
            if (student.getMiddleName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty middle name");
            if (student.getGender() == null)
                throw new StudentDaoException(
                        "Student's gender is required");
            if (student.getGender().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty gender");
            if (!(student.getGender().trim().equalsIgnoreCase("Male") ||
                    student.getGender().trim().equalsIgnoreCase("Female")))
                throw new StudentDaoException("Student's gender is invalid");
            if (student.getAge() == 0)
                throw new StudentDaoException("Student's age is required");
            if(student.getAge() <= 14 || student.getAge() > 60)
                throw new StudentDaoException("Student's age is invalid. " +
                        "The minimum age is 14 and the maximum age is 60");
            if(sectionId > 0) {
                Section section = sectionDao.getSectionById(sectionId);
                student.setSection(section);
            }else
                student.setSection(null);
            student = (Student) session.merge(student);
            session.getTransaction().commit();
            session.close();
           return student;
        }catch (StudentDaoException | SectionException e){
            session.close();
            throw new StudentException(e.getMessage());
        }
    }

    @Override
    public Student updateStudentById(long id, Student newStudent, long sectionId) throws StudentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, id);
            if (student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist.");
            if(newStudent == null)
                newStudent = new Student();
            if (newStudent.getStudentNumber() < 1)
                student.setStudentNumber(newStudent.getStudentNumber());
            if (!(newStudent.getFirstName() != null ? newStudent.getFirstName() : "").trim().isEmpty())
                student.setFirstName(newStudent.getFirstName());
            if (!(newStudent.getLastName() != null ? newStudent.getLastName() : "").trim().isEmpty())
                student.setLastName(newStudent.getLastName());
            if (!(newStudent.getMiddleName() != null ? newStudent.getMiddleName() : "").trim().isEmpty())
                student.setMiddleName(newStudent.getMiddleName());
            if (!(newStudent.getGender() != null ? newStudent.getGender() : "").trim().isEmpty()) {
                if (!(newStudent.getGender().trim().equalsIgnoreCase("Male") ||
                        newStudent.getGender().trim().equalsIgnoreCase("Female")))
                    throw new StudentDaoException("Student's gender is invalid");
                student.setGender(newStudent.getGender());
            }
            if (newStudent.getAge() != 0) {
                if(newStudent.getAge() <= 14 || newStudent.getAge() > 60)
                    throw new StudentDaoException("Student's age is invalid. " +
                            "The minimum age is 14 and the maximum age is 60");
                student.setAge(newStudent.getAge());
            }
            if(sectionId > 0) {
                Section section = sectionDao.getSectionById(sectionId);
                if(section.getId() == (student.getSection() != null ? student.getSection().getId() : 0))
                    throw new StudentDaoException("Can't update student's section with same section");
                student.setSection(section);
                student = (Student) session.merge(student);
            }
            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException | SectionException e) {
            session.close();
            throw new StudentException(e.getMessage());
        }
    }

    @Override
    public Student deleteStudentById(long id) throws StudentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, id);
            String[] table = new String[8];
            table[0] = ActivityResult.class.getSimpleName();
            table[1] = AssignmentResult.class.getSimpleName();
            table[2] = AttendanceResult.class.getSimpleName();
            table[3] = ExamResult.class.getSimpleName();
            table[4] = ProjectResult.class.getSimpleName();
            table[5] = QuizResult.class.getSimpleName();
            table[6] = RecitationResult.class.getSimpleName();
            table[7] = Grade.class.getSimpleName();

            if (student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist.");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where student.id = :studentId");
                Query query = session.createQuery(hql);
                query.setParameter("studentId", id);
                query.executeUpdate();
            }

            Query classQuery = session.createQuery("from Class");
            for(Object classObj : classQuery.list()){
                Class _class = (Class) classObj;
                _class = session.get(Class.class, _class.getId());
                for(Student s : _class.getStudentList()) {
                    if(s == null)
                        continue;
                    if (s.getId() == id) {
                        _class.getStudentList().remove(s);
                        break;
                    }
                }
            }

            String hql = "";

            student.setSection(null);
            session.delete(student);
            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException e) {
            session.close();
            throw new StudentException(e.getMessage());
        }
    }
}
