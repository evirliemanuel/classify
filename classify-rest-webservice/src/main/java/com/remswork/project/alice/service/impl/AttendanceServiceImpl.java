package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.AttendanceDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.AttendanceResult;
import com.remswork.project.alice.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDaoImpl attendanceDao;


    @Override
    public Attendance getAttendanceById(long id) throws GradingFactorException {
        return attendanceDao.getAttendanceById(id);
    }

    @Override
    public List<Attendance> getAttendanceList() throws GradingFactorException {
        return attendanceDao.getAttendanceList();
    }

    @Override
    public List<Attendance> getAttendanceListByClassId(long classId) throws GradingFactorException {
        return attendanceDao.getAttendanceListByClassId(classId);
    }

    @Override
    public List<Attendance> getAttendanceListByClassId(long classId, long termId) throws GradingFactorException {
        return attendanceDao.getAttendanceListByClassId(classId, termId);
    }

    @Override
    public List<Attendance> getAttendanceListByStudentId(long studentId) throws GradingFactorException {
        return attendanceDao.getAttendanceListByStudentId(studentId);
    }

    @Override
    public List<Attendance> getAttendanceListByStudentId(long studentId, long termId) throws GradingFactorException {
        return attendanceDao.getAttendanceListByStudentId(studentId, termId);
    }

    @Override
    public AttendanceResult getAttendanceResultById(long id) throws GradingFactorException {
        return attendanceDao.getAttendanceResultById(id);
    }

    @Override
    public AttendanceResult getAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException {
        return attendanceDao.getAttendanceResultByAttendanceAndStudentId(attendanceId, studentId);
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long classId) throws GradingFactorException {
        return attendanceDao.addAttendance(attendance, classId);
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long classId, long termId) throws GradingFactorException {
        return attendanceDao.addAttendance(attendance, classId, termId);
    }

    @Override
    public AttendanceResult addAttendanceResult(int score, long attendanceId, long studentId) throws GradingFactorException {
        return attendanceDao.addAttendanceResult(score, attendanceId, studentId);
    }

    @Override
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long classId) throws GradingFactorException {
        return attendanceDao.updateAttendanceById(id, newAttendance, classId);
    }

    @Override
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long classId, long termId)
            throws GradingFactorException {
        return attendanceDao.updateAttendanceById(id, newAttendance, classId, termId);
    }

    @Override
    public AttendanceResult updateAttendanceResultByAttendanceAndStudentId(int score, long attendanceId, long studentId)
            throws GradingFactorException {
        return attendanceDao.updateAttendanceResultByAttendanceAndStudentId(score, attendanceId, studentId);
    }

    @Override
    public Attendance deleteAttendanceById(long id) throws GradingFactorException {
        return attendanceDao.deleteAttendanceById(id);
    }

    @Override
    public AttendanceResult deleteAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException {
        return attendanceDao.deleteAttendanceResultByAttendanceAndStudentId(attendanceId, studentId);
    }
}