package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ClassDao;
import com.remswork.project.alice.dao.exception.ClassDaoException;
import com.remswork.project.alice.exception.*;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.model.Class;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ClassDaoImpl implements ClassDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TeacherDaoImpl teacherDao;
    @Autowired
    private SubjectDaoImpl subjectDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;
    @Autowired
    private SectionDaoImpl sectionDao;
    @Autowired
    private StudentDaoImpl studentDao;

    @Override
    public Class getClassById(long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public List<Class> getClassList() throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Class> classList = new ArrayList<>();
            Query query = session.createQuery("from Class");
            for (Object classObject : query.list())
                classList.add((Class) classObject);
            session.getTransaction().commit();
            session.close();
            return classList;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public List<Class> getClassListByTeacherId(long teacherId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Class> classList = new ArrayList<>();
            Query query = session.createQuery("from Class as c join c.teacher as t where t.id = :teacherId");
            query.setParameter("teacherId", teacherId);
            for (Object classObject : query.list())
                classList.add((Class)((Object[]) classObject)[0]);
            session.getTransaction().commit();
            session.close();
            return classList;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public List<Class> getClassListByStudentId(long studentId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Class> classList = new ArrayList<>();
            Query query = session.createQuery("from Class as c join c.studentList as st where st.id = :studentId");
            query.setParameter("studentId", studentId);
            for (Object classObject : query.list())
                classList.add((Class)((Object[]) classObject)[0]);
            session.getTransaction().commit();
            session.close();
            return classList;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public List<Class> getClassListBySubjectId(long subjectId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Class> classList = new ArrayList<>();
            Query query = session.createQuery("from Class as c join c.subject as su where su.id = :subjectId");
            query.setParameter("subjectId", subjectId);
            for (Object classObject : query.list())
                classList.add((Class)((Object[]) classObject)[0]);
            session.getTransaction().commit();
            session.close();
            return classList;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule getScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Schedule schedule = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id)
                    schedule = s;
            }
            if (schedule == null)
                throw new ClassDaoException("Class's schedule with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Set<Schedule> getScheduleList(long classId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            Set<Schedule> scheduleSet = _class.getScheduleList();
            session.getTransaction().commit();
            session.close();
            return scheduleSet;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Student getStudentById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Student student = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Student s : _class.getStudentList()) {
                if (s.getId() == id)
                    student = s;
            }
            if (student == null)
                throw new ClassDaoException("Class's student with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return student;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Set<Student> getStudentList(long classId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            Set<Student> studentSet = _class.getStudentList();
            session.getTransaction().commit();
            session.close();
            return studentSet;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class addClass(Class _class, long teacherId, long subjectId, long sectionId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (_class == null)
                throw new ClassDaoException("You tried to add class with a null value");

            _class.setTeacher(null);
            _class.setSubject(null);
            _class.setSection(null);

            if (teacherId != 0) {
                Teacher teacher = teacherDao.getTeacherById(teacherId);
                _class.setTeacher(teacher);
            }
            if (subjectId != 0) {
                Subject subject = subjectDao.getSubjectById(subjectId);
                _class.setSubject(subject);
            }
            if (sectionId != 0) {
                Section section = sectionDao.getSectionById(sectionId);
                _class.setSection(section);
            }
            _class = (Class) session.merge(_class);
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException | TeacherException | SubjectException | SectionException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule addScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Schedule schedule = scheduleDao.getScheduleById(id);
            String hql = "from Class as c join c.subject as su join c.scheduleList as sc where sc.id = :scheduleId";
            Query query = session.createQuery("from Class as c join c.scheduleList as sc where sc.id = :scheduleId");
            query.setParameter("scheduleId", id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            if(query.list().size() > 0)
                throw new ClassDaoException("Schedule with id : " + id + " already exist in a class");
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id)
                    throw new ClassDaoException("Class's schedule with id : " + id + " already exist");
            }
            _class.getScheduleList().add(schedule);
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException | ScheduleException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Student addStudentById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            Student student = studentDao.getStudentById(id);
            for (Student s : _class.getStudentList()) {
                if (s.getId() == id)
                    throw new ClassDaoException("Class's student with id : " + id + " already exist");
            }
            _class.getStudentList().add(student);
            session.merge(_class);
            session.getTransaction().commit();
            session.close();
            return student;
        } catch (ClassDaoException | StudentException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class updateClassById(long id, Class newClass, long teacherId, long subjectId, long sectionId)
            throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");
            if (teacherId != 0) {
                Teacher teacher = teacherDao.getTeacherById(teacherId);
                if((_class.getTeacher() != null ? _class.getTeacher().getId() : 0) == teacher.getId())
                    throw new ClassDaoException("Can't update class's teacher with same teacher");
                _class.setTeacher(teacher);
            }
            if (subjectId != 0) {
                Subject subject = subjectDao.getSubjectById(subjectId);
                if((_class.getSubject() != null ? _class.getSubject().getId() : 0) == subject.getId())
                    throw new ClassDaoException("Can't update class's subject with same subject");
                _class.setSubject(subject);
            }
            if (sectionId != 0) {
                Section section = sectionDao.getSectionById(sectionId);
                if((_class.getSection() != null ? _class.getSection().getId() : 0) == section.getId())
                    throw new ClassDaoException("Can't update class's section with same section");
                _class.setSection(section);
            }
            _class = (Class) session.merge(_class);
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException | TeacherException | SubjectException | SectionException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class deleteClassById(long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {

            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");

            _class.setTeacher(null);
            _class.setSubject(null);
            _class.setScheduleList(null);
            _class.setSection(null);
            _class.setStudentList(null);

            for(Object object : session.createQuery("from Activity where _class.id = '" + id + "'").list()) {
                Activity activity = (Activity) object;
                String hql = "delete from ActivityResult where activity.id = :activityId";
                Query query = session.createQuery(hql);
                query.setParameter("activityId", activity.getId());
                query.executeUpdate();
                session.delete(activity);
            }

            for(Object object : session.createQuery("from Assignment where _class.id = '" + id + "'").list()) {
                Assignment assignment = (Assignment) object;
                String hql = "delete from AssignmentResult where assignment.id = :assignmentId";
                Query query = session.createQuery(hql);
                query.setParameter("assignmentId", assignment.getId());
                query.executeUpdate();
                session.delete(assignment);
            }

            for(Object object : session.createQuery("from Attendance where _class.id = '" + id + "'").list()) {
                Attendance attendance = (Attendance) object;
                String hql = "delete from AttendanceResult where attendance.id = :attendanceId";
                Query query = session.createQuery(hql);
                query.setParameter("attendanceId", attendance.getId());
                query.executeUpdate();
                session.delete(attendance);
            }

            for(Object object : session.createQuery("from Exam where _class.id = '" + id + "'").list()) {
                Exam exam = (Exam) object;
                String hql = "delete from ExamResult where exam.id = :examId";
                Query query = session.createQuery(hql);
                query.setParameter("examId", exam.getId());
                query.executeUpdate();
                session.delete(exam);
            }

            for(Object object : session.createQuery("from Project where _class.id = '" + id + "'").list()) {
                Project project = (Project) object;
                String hql = "delete from ProjectResult where project.id = :projectId";
                Query query = session.createQuery(hql);
                query.setParameter("projectId", project.getId());
                query.executeUpdate();
                session.delete(project);
            }

            for(Object object : session.createQuery("from Quiz where _class.id = '" + id + "'").list()) {
                Quiz quiz = (Quiz) object;
                String hql = "delete from QuizResult where quiz.id = :quizId";
                Query query = session.createQuery(hql);
                query.setParameter("quizId", quiz.getId());
                query.executeUpdate();
                session.delete(quiz);
            }

            for(Object object : session.createQuery("from Recitation where _class.id = '" + id + "'").list()) {
                Recitation recitation = (Recitation) object;
                String hql = "delete from RecitationResult where recitation.id = :recitationId";
                Query query = session.createQuery(hql);
                query.setParameter("recitationId", recitation.getId());
                query.executeUpdate();
                session.delete(recitation);
            }

            String hql = "delete from Grade as G where G._class.id = :classId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", id);
            query.executeUpdate();

            session.delete(_class);
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule deleteScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Schedule schedule = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id) {
                    _class.getScheduleList().remove(s);
                    schedule = s;
                    break;
                }
            }
            if (schedule == null)
                throw new ClassDaoException("Class's schedule with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Student deleteStudentById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Student student = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Student s : _class.getStudentList()) {
                if (s.getId() == id) {
                    _class.getStudentList().remove(s);
                    student = s;
                    break;
                }
            }
            if (student == null)
                throw new ClassDaoException("Class's student with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return student;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }
}
