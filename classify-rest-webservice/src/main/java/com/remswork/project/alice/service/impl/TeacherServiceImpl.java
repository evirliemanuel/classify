package com.remswork.project.alice.service.impl;

import java.util.List;

import com.remswork.project.alice.exception.TeacherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.dao.impl.TeacherDaoImpl;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDaoImpl teacherDao;

	@Override
	public Teacher getTeacherById(final long id) throws TeacherException {
		return teacherDao.getTeacherById(id);
	}
	
	@Override
	public List<Teacher> getTeacherList() throws TeacherException {
		return teacherDao.getTeacherList();
	}
	
	@Override
	public Teacher addTeacher(final Teacher teacher) throws TeacherException {
		return teacherDao.addTeacher(teacher);
	}

	@Override
	public Teacher addTeacher(Teacher teacher, long departmentId) throws TeacherException {
		return teacherDao.addTeacher(teacher, departmentId);
	}

	@Override
	public Teacher updateTeacherById(final long id, final Teacher newTeacher) throws TeacherException {
		 return teacherDao.updateTeacherById(id, newTeacher);
	}

	@Override
	public Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) throws TeacherException {
		return teacherDao.updateTeacherById(id, newTeacher, departmentId);
	}

	@Override
	public Teacher deleteTeacherById(final long id) throws TeacherException {
		return teacherDao.deleteTeacherById(id);
	}

}
