package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.Student;

import java.util.List;
import java.util.Set;

public interface ClassService {

    Class getClassById(long id) throws ClassException;

    List<Class> getClassList() throws ClassException;

    List<Class> getClassListByTeacherId(long teacherId) throws ClassException;

    List<Class> getClassListByStudentId(long studentId) throws ClassException;

    List<Class> getClassListBySubjectId(long subjectId) throws ClassException;

    Schedule getScheduleById(long classId, long id) throws ClassException;

    Set<Schedule> getScheduleList(long classId) throws ClassException;

    Student getStudentById(long classId, long id) throws ClassException;

    Set<Student> getStudentList(long classId) throws ClassException;

    Class addClass(Class _class, long teacherId, long subjectId, long sectionId) throws ClassException;

    Schedule addScheduleById(long classId, long id) throws ClassException;

    Student addStudentById(long classId, long id) throws ClassException;

    Class updateClassById(long id, Class newClass, long teacherId, long subjectId, long sectionId) throws ClassException;

    Class deleteClassById(long id) throws ClassException;

    Schedule deleteScheduleById(long classId, long id) throws ClassException;

    Student deleteStudentById(long classId, long id) throws ClassException;
}
