package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.DepartmentDaoImpl;
import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDaoImpl departmentDao;

    @Override
    public Department getDepartmentById(final long id) throws DepartmentException {
        return departmentDao.getDepartmentById(id);
    }

    @Override
    public List<Department> getDepartmentList() throws DepartmentException {
       return departmentDao.getDepartmentList();
    }

    @Override
    public Department addDepartment(final Department department) throws DepartmentException {
        return departmentDao.addDepartment(department);
    }

    @Override
    public Department updateDepartmentById(final long id, final Department newDepartment) throws DepartmentException {
        return departmentDao.updateDepartmentById(id, newDepartment);
    }

    @Override
    public Department deleteDepartmentById(final long id) throws DepartmentException {
        return departmentDao.deleteDepartmentById(id);
    }
}

