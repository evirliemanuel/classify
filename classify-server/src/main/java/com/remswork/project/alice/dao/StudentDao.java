package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Student;

import java.util.List;

public interface StudentDao {

    Student getStudentById(long id) throws StudentException;

    List<Student> getStudentList() throws StudentException;

    Student addStudent(Student student, long sectionId) throws StudentException;

    Student updateStudentById(long id, Student newStudent, long sectionId) throws StudentException;

    Student deleteStudentById(long id) throws StudentException;
}
