package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.StudentDaoImpl;
import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDaoImpl studentDao;

    @Override
    public Student getStudentById(long id) throws StudentException {
        return studentDao.getStudentById(id);
    }

    @Override
    public List<Student> getStudentList() throws StudentException {
        return studentDao.getStudentList();
    }

    @Override
    public Student addStudent(Student student, long sectionId) throws StudentException {
        return studentDao.addStudent(student, sectionId);
    }

    @Override
    public Student updateStudentById(long id, Student newStudent, long sectionId) throws StudentException {
        return studentDao.updateStudentById(id, newStudent, sectionId);
    }

    @Override
    public Student deleteStudentById(long id) throws StudentException {
       return studentDao.deleteStudentById(id);
    }
}
