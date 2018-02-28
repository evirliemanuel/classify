package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.AttendanceResult;

import java.util.List;

public interface AttendanceDao {

    Attendance getAttendanceById(long id) throws GradingFactorException;

    List<Attendance> getAttendanceList() throws GradingFactorException;

    List<Attendance> getAttendanceListByClassId(long classId) throws GradingFactorException;

    List<Attendance> getAttendanceListByClassId(long classId, long termId) throws GradingFactorException;

    List<Attendance> getAttendanceListByStudentId(long studentId) throws GradingFactorException;

    List<Attendance> getAttendanceListByStudentId(long studentId, long termId) throws GradingFactorException;

    AttendanceResult getAttendanceResultById(long id) throws GradingFactorException;

    AttendanceResult getAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException;

    Attendance addAttendance(Attendance attendance, long classId) throws GradingFactorException;

    Attendance addAttendance(Attendance attendance, long classId, long termId) throws GradingFactorException;

    AttendanceResult addAttendanceResult(int status, long attendanceId, long studentId) throws GradingFactorException;

    Attendance updateAttendanceById(long id, Attendance newAttendance, long classId)
            throws GradingFactorException;

    Attendance updateAttendanceById(long id, Attendance newAttendance, long classId, long termId)
            throws GradingFactorException;

    AttendanceResult updateAttendanceResultByAttendanceAndStudentId(int status, long attendanceId, long studentId)
            throws GradingFactorException;

    Attendance deleteAttendanceById(long attendanceId) throws GradingFactorException;

    AttendanceResult deleteAttendanceResultByAttendanceAndStudentId(long attendanceId, long studentId)
            throws GradingFactorException;
}
