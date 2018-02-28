package com.remswork.project.alice.service;

import java.util.List;

import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;

public interface TeacherService {

    Teacher getTeacherById(long id) throws TeacherException;

    List<Teacher> getTeacherList() throws TeacherException;

    Teacher addTeacher(Teacher teacher) throws TeacherException;

    Teacher addTeacher(Teacher teacher, long departmentId) throws TeacherException;

    Teacher updateTeacherById(long id, Teacher newTeacher) throws TeacherException;

    Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) throws TeacherException;

    Teacher deleteTeacherById(long id) throws TeacherException;
}
