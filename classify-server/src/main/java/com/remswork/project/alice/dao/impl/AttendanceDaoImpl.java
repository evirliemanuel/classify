package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.AttendanceDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.dao.exception.StudentDaoException;
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
public class AttendanceDaoImpl implements AttendanceDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Attendance getAttendanceById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Attendance> attendanceList = new ArrayList<>();
            Query query = session.createQuery("from Attendance");
            for (Object objAttendance : query.list())
                attendanceList.add((Attendance) objAttendance);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceListByClassId(long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Attendance> attendanceList = new ArrayList<>();

            Query query = session.createQuery("from Attendance where _class.id = :classId");
            query.setParameter("classId", classId);
            for (Object objAttendance : query.list())
                attendanceList.add((Attendance) objAttendance);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceListByClassId(long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Attendance> attendanceList = new ArrayList<>();
            String hql = "from Attendance where _class.id = :classId and term.id = :termId";

            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("termId", termId);

            for (Object objAttendance : query.list())
                attendanceList.add((Attendance) objAttendance);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            List<Attendance> attendanceList = new ArrayList<>();
            String hql = "from AttendanceResult as R join R.attendance where R.student.id = :studentId";

            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            for (Object object : query.list())
                attendanceList.add((Attendance) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);
            List<Attendance> attendanceList = new ArrayList<>();
            String hql = "from AttendanceResult as R join R.attendance as A where R.student.id = :studentId and " +
                    "A.term.id = :termId";

            if (term == null)
                throw new GradingFactorDaoException("Attendance's term with id : " + termId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");

            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for (Object object : query.list())
                attendanceList.add((Attendance) ((Object[]) object)[1]);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AttendanceResult getAttendanceResultById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            AttendanceResult result = session.get(AttendanceResult.class, id);
            if(result == null)
                throw new GradingFactorDaoException("AttendanceResult with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return result;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AttendanceResult getAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, attendanceId);
            Student student = session.get(Student.class, studentId);
            String hql = "from AttendanceResult as R where R.attendance.id = :attendanceId and R.student.id = :studentId";

            Query query = session.createQuery(hql);
            query.setParameter("attendanceId", attendanceId);
            query.setParameter("studentId", studentId);

            if (attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + attendanceId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");
            if (query.list().size() < 1)
                throw new GradingFactorDaoException("No AttendanceResult found. Try use query param " +
                        "(ex. studentId=[id])");

            AttendanceResult result = (AttendanceResult) query.list().get(0);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long classId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);

            if (attendance == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (classId == 0)
                throw new GradingFactorDaoException("Query param : classId is required");
            if (_class == null)
                throw new GradingFactorDaoException("Attendance's class with id : " + classId + " does not exist");
            if (attendance.getTitle() == null)
                throw new GradingFactorDaoException("Attendance's title is required");
            if (attendance.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Attendance can't have an empty title");
            if (attendance.getDate() == null)
                throw new GradingFactorDaoException("Attendance's date is required");
            if (attendance.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Attendance can't have an empty date");

            attendance.set_class(_class);
            session.persist(attendance);
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if (attendance == null)
                throw new GradingFactorDaoException("You tried to add class with a null value");
            if (termId < 1)
                throw new GradingFactorDaoException("Query param : termId has an invalid input");
            if (_class == null)
                throw new GradingFactorDaoException("Attendance's class with id : " + classId + " does not exist");
            if (term == null)
                throw new GradingFactorDaoException("Attendance's term with id : " + termId + " does not exist");
            if (attendance.getTitle() == null)
                throw new GradingFactorDaoException("Attendance's title is required");
            if (attendance.getTitle().trim().equals(""))
                throw new GradingFactorDaoException("Attendance can't have an empty title");
            if (attendance.getDate() == null)
                throw new GradingFactorDaoException("Attendance's date is required");
            if (attendance.getDate().trim().equals(""))
                throw new GradingFactorDaoException("Attendance can't have an empty date");

            attendance.set_class(_class);
            attendance.setTerm(term);

            session.persist(attendance);
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AttendanceResult addAttendanceResult(int status, long attendanceId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, attendanceId);
            Student student = session.get(Student.class, studentId);
            AttendanceResult result = new AttendanceResult();
            String hql = "from AttendanceResult as R where R.attendance.id = :attendanceId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("attendanceId", attendanceId);
            query.setParameter("studentId", studentId);

            if (attendance == null)
                throw new GradingFactorDaoException("Attendance's attendance with id : " + attendanceId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");
            if (attendanceId < 1)
                throw new GradingFactorDaoException("Query param : attendanceId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() > 0)
                throw new GradingFactorDaoException("AttendanceResult's  student with id : " + studentId +
                        " already exist");

            result.setStatus(status);
            result.setAttendance(attendance);
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
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long classId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            Class _class = session.get(Class.class, classId);

            if(newAttendance == null)
                newAttendance = new Attendance();
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Attendance's class with id : " + classId + " does not exist");
            if(!(newAttendance.getTitle() != null ? newAttendance.getTitle() : "").trim().isEmpty())
                attendance.setTitle(newAttendance.getTitle());
            if(!(newAttendance.getDate() != null ? newAttendance.getDate() : "").trim().isEmpty())
                attendance.setDate(newAttendance.getDate());
            if(classId > 0) {
                if(classId == (attendance.get_class() != null ? attendance.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Attendance's  class with id : " + classId + " already exist");
                attendance.set_class(_class);
            }
            session.getTransaction().commit();
            session.close();
            return attendance;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long classId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            Class _class = session.get(Class.class, classId);
            Term term = session.get(Term.class, termId);

            if(newAttendance == null)
                newAttendance = new Attendance();
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");
            if (_class == null && classId != 0)
                throw new GradingFactorDaoException("Attendance's class with id : " + classId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorDaoException("Attendance's term with id : " + termId + " does not exist");
            if(!(newAttendance.getTitle() != null ? newAttendance.getTitle() : "").trim().isEmpty())
                attendance.setTitle(newAttendance.getTitle());
            if(!(newAttendance.getDate() != null ? newAttendance.getDate() : "").trim().isEmpty())
                attendance.setDate(newAttendance.getDate());
            if(classId > 0) {
                if(classId == (attendance.get_class() != null ? attendance.get_class().getId() : 0))
                    throw new GradingFactorDaoException("Attendance's  class with id : " + classId + " already exist");
                attendance.set_class(_class);
            }
            if(termId > 0) {
                if(termId != (attendance.getTerm() != null ? attendance.getTerm().getId() : 0))
                    attendance.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return attendance;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AttendanceResult updateAttendanceResultByAttendanceAndStudentId(int status, long attendanceId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, attendanceId);
            Student student = session.get(Student.class, studentId);

            String hql = "from AttendanceResult as R where R.attendance.id = :attendanceId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("attendanceId", attendanceId);
            query.setParameter("studentId", studentId);

            if (attendance == null)
                throw new GradingFactorDaoException("Attendance's attendance with id : " + attendanceId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");
            if (attendanceId < 1)
                throw new GradingFactorDaoException("Query param : attendanceId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            AttendanceResult result = (AttendanceResult) query.list().get(0);

            result.setStatus(status);
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance deleteAttendanceById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String[] table = new String[1];
            table[0] = AttendanceResult.class.getSimpleName();

            Attendance attendance = session.get(Attendance.class, id);
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");

            for(String cell : table) {
                String hql = "delete from ".concat(cell).concat(" where attendance.id = :attendanceId");
                Query query = session.createQuery(hql);
                query.setParameter("attendanceId", id);
                query.executeUpdate();
            }
            session.delete(attendance);
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public AttendanceResult deleteAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, attendanceId);
            Student student = session.get(Student.class, studentId);

            String hql = "from AttendanceResult as R where R.attendance.id = :attendanceId and R.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("attendanceId", attendanceId);
            query.setParameter("studentId", studentId);

            if (attendance == null)
                throw new GradingFactorDaoException("Attendance's attendance with id : " + attendanceId + " does not exist");
            if (student == null)
                throw new GradingFactorDaoException("Attendance's student with id : " + studentId + " does not exist");
            if (attendanceId < 1)
                throw new GradingFactorDaoException("Query param : attendanceId is required");
            if (studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required");
            if(query.list().size() < 1)
                throw new GradingFactorDaoException("No result to delete");
            AttendanceResult result = (AttendanceResult) query.list().get(0);

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
