package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.DepartmentDao;
import com.remswork.project.alice.dao.exception.DepartmentDaoException;
import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Teacher;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Department getDepartmentById(long id) throws DepartmentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Department department = session.get(Department.class, id);
            if(department == null)
                throw new DepartmentDaoException("Department with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return department;
        }catch (DepartmentDaoException e){
            session.close();
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public List<Department> getDepartmentList() throws DepartmentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Department> departmentList = new ArrayList<>();
        try{
            Query query = session.createQuery("from Department");
            for(Object departmentObj : query.list())
                departmentList.add((Department) departmentObj);
            session.getTransaction().commit();
            session.close();
            return departmentList;
        }catch (DepartmentDaoException e){
            session.close();
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public Department addDepartment(Department department) throws DepartmentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if(department == null)
                throw new DepartmentDaoException("You tried to add department with a null value");
            if(department.getName() == null)
                throw new DepartmentDaoException("Department's name is required");
            if(department.getName().trim().equals(""))
                throw new DepartmentDaoException("Department can't have an empty name");
            if(department.getDescription() == null)
                throw new DepartmentDaoException("Department's description is required");
            if(department.getDescription().trim().equals(""))
                throw new DepartmentDaoException( "Department can't have an empty description");

            session.save(department);
            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e) {
            session.close();
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public Department updateDepartmentById(long id, Department newDepartment) throws DepartmentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Department department = session.get(Department.class, id);
            if(department == null)
                throw new DepartmentDaoException("Department with id : " + id + " does not exist.");
            if(newDepartment == null)
                throw new DepartmentDaoException("You tried to update department with a null value");
            if(!(newDepartment.getName()!=null?newDepartment.getName().trim():"").isEmpty())
                department.setName(newDepartment.getName());
            if(!(newDepartment.getDescription()!=null?newDepartment.getDescription().trim():"").isEmpty())
                department.setDescription(newDepartment.getDescription());

            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e){
            session.close();
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public Department deleteDepartmentById(long id) throws DepartmentException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Department department = session.get(Department.class, id);
            if(department == null)
                throw new DepartmentDaoException("Department with id : " + id + " does not exist.");

            //to avoid the constraints restriction we meed to remove department from the teacher
            // that having the department
            Query teacherQuery = session.createQuery("from Teacher");
            for(Object teacherObj : teacherQuery.list()){
                Teacher teacher = (Teacher) teacherObj;
                if(teacher.getDepartment() == null)
                    continue;
                if(teacher.getDepartment().equals(department) ||
                        (teacher.getDepartment()!=null?teacher.getDepartment().getId():0)==department.getId()) {
                    teacher.setDepartment(null);
                }
            }
            //to avoid the constraints restriction we meed to delete the section that having the department
            Query sectionQuery = session.createQuery("from Section");
            for(Object sectionObj : sectionQuery.list()){
                Section section = (Section) sectionObj;
                if(section.getDepartment() == null)
                    continue;
                if(section.getDepartment().equals(department) || (section.getDepartment() != null ?
                        section.getDepartment().getId() : 0) == department.getId()) {
                    session.delete(section);
                }
            }
            //to avoid the constraints restriction we meed to remove department from the class's teacher and section
            // that having the department
            Query classQuery = session.createQuery("from Class");
            for(Object classObj : classQuery.list()) {
                Class _class = (Class) classObj;
                Teacher teacher = _class.getTeacher();
                Section section = _class.getSection();
                if(teacher != null) {
                    if (teacher.getDepartment() != null) {
                        if (teacher.getDepartment().equals(department) || (teacher.getDepartment() != null ?
                                teacher.getDepartment().getId() : 0) == department.getId()) {
                            teacher.setDepartment(null);
                        }
                    }
                }
                if(section != null) {
                    if (section.getDepartment() != null) {
                        if(section.getDepartment().equals(department) || (section.getDepartment() != null ?
                                section.getDepartment().getId() : 0) == department.getId()) {
                            _class.setSection(null);
                        }
                    }
                }
            }
            session.delete(department);
            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e) {
            session.close();
            throw new DepartmentException(e.getMessage());
        }
    }
}
