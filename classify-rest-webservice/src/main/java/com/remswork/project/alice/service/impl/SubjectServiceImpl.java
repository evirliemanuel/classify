package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.SubjectDaoImpl;
import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDaoImpl subjectDao;

    @Override
    public Subject getSubjectById(long id) throws SubjectException {
        return subjectDao.getSubjectById(id);
    }

    @Override
    public Subject getSubjectByClassAndTeacherId(long classId, long teacherId) throws SubjectException {
        return subjectDao.getSubjectByClassAndTeacherId(classId, teacherId);
    }

    @Override
    public Subject getSubjectByScheduleId(long scheduleId) throws SubjectException {
        return subjectDao.getSubjectByScheduleId(scheduleId);
    }

    @Override
    public List<Subject> getSubjectList() throws SubjectException {
       return subjectDao.getSubjectList();
    }

    @Override
    public List<Subject> getSubjectListByTeacherId(long teacherId) throws SubjectException {
        return subjectDao.getSubjectListByTeacherId(teacherId);
    }

    @Override
    public List<Subject> getSubjectListByTeacherIdUnique(long teacherId) throws SubjectException {
        return subjectDao.getSubjectListByTeacherIdUnique(teacherId);
    }

    @Override
    public List<Subject> getSubjectListByStudentId(long studentId) throws SubjectException {
        return subjectDao.getSubjectListByStudentId(studentId);
    }

    @Override
    public Subject addSubject(Subject subject) throws SubjectException {
        return subjectDao.addSubject(subject);
    }

    @Override
    public Subject updateSubjectById(long id, Subject newSubject) throws SubjectException {
        return subjectDao.updateSubjectById(id, newSubject);
    }

    @Override
    public Subject deleteSubjectById(long id) throws SubjectException {
        return subjectDao.deleteSubjectById(id);
    }
}
