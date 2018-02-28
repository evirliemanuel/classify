package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.SectionDao;
import com.remswork.project.alice.dao.exception.SectionDaoException;
import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SectionDaoImpl implements SectionDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DepartmentDaoImpl departmentDao;

    @Override
    public Section getSectionById(long id) throws SectionException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Section section = session.get(Section.class, id);
            if(section == null)
                throw new SectionDaoException("Section with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return section;
        }catch (SectionDaoException e) {
            session.close();
            throw new SectionException(e.getMessage());
        }
    }

    @Override
    public List<Section> getSectionList() throws SectionException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Section> sectionList = new ArrayList<>();
            Query query = session.createQuery("from Section");
            for(Object sectionObj : query.list())
                sectionList.add((Section) sectionObj);
            session.getTransaction().commit();
            session.close();
            return sectionList;
        }catch (SectionDaoException e) {
            session.close();
            throw new SectionException(e.getMessage());
        }
    }

    @Override
    public Section addSection(Section section, long departmentId) throws SectionException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if(section == null)
                throw new SectionDaoException("You tried to add section with a null value");
            if(section.getName() == null)
                throw new SectionDaoException("Section's first name is required");
            if(section.getName().trim().equals(""))
                throw new SectionDaoException("Section can't have an empty name");
            if(departmentId < 1)
                throw new SectionDaoException("Query param : departmentId is required");

            Department department = departmentDao.getDepartmentById(departmentId);
            section.setDepartment(department);
            section = (Section) session.merge(section);
            session.getTransaction().commit();
            session.close();
            return section;
        }catch (SectionDaoException | DepartmentException e) {
            session.close();
            throw new SectionException(e.getMessage());
        }
    }

    @Override
    public Section updateSectionById(long id, Section newSection, long departmentId) throws SectionException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Section section = session.get(Section.class, id);
            if(section == null)
                throw new SectionDaoException("Section with id : " + id + " does not exist");
            if(newSection == null)
                newSection = new Section();
            if(!(newSection.getName() != null ? newSection.getName() : "").trim().isEmpty())
                section.setName(newSection.getName().trim());
            if(departmentId > 0) {
                Department department = departmentDao.getDepartmentById(departmentId);
                if(department.getId() == (section.getDepartment() != null ? section.getDepartment().getId() : 0))
                    throw new SectionDaoException("Can't update section's department with same department");
                section.setDepartment(department);
            }
            session.getTransaction().commit();
            session.close();
            return section;
        }catch (SectionDaoException | DepartmentException e) {
            session.close();
            throw new SectionException(e.getMessage());
        }
    }

    @Override
    public Section deleteSectionById(long id) throws SectionException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Section section = session.get(Section.class, id);
            if(section == null)
                throw new SectionDaoException("Section with id : " + id + " does not exist");

            //to avoid the constraints restriction we meed to remove section from the student that having the section
            Query studentQuery = session.createQuery("from Student");
            for(Object studentObj : studentQuery.list()){
                Student student = (Student) studentObj;
                if(student.getSection() == null)
                    continue;
                if(student.getSection().equals(section) ||
                        (student.getSection()!=null?student.getSection().getId():0)==section.getId()) {
                    student.setSection(null);
                }
            }

            //to avoid the constraints restriction we meed to remove section from the class that having the section
            Query classQuery = session.createQuery("from Class");
            for(Object classObj : classQuery.list()){
                Class _class = (Class) classObj;
                if(_class.getSection() == null)
                    continue;
                if(_class.getSection().equals(section) ||
                        (_class.getSection()!=null?_class.getSection().getId():0)==section.getId()) {
                    _class.setSection(null);
                }
            }
            section.setDepartment(null);
            session.delete(section);
            session.getTransaction().commit();
            session.close();
            return section;
        }catch (SectionDaoException  e) {
            session.close();
            throw new SectionException(e.getMessage());
        }
    }
}
