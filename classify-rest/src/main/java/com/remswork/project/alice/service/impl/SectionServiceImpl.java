package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.SectionDaoImpl;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDaoImpl sectionDao;

    @Override
    public Section getSectionById(long id) throws SectionException {
        return sectionDao.getSectionById(id);
    }

    @Override
    public List<Section> getSectionList() throws SectionException {
        return sectionDao.getSectionList();
    }

    @Override
    public Section addSection(Section section, long departmentId) throws SectionException {
        return sectionDao.addSection(section, departmentId);
    }

    @Override
    public Section updateSectionById(long id, Section newSection, long departmentId) throws SectionException {
        return sectionDao.updateSectionById(id, newSection, departmentId);
    }

    @Override
    public Section deleteSectionById(long id) throws SectionException {
        return sectionDao.deleteSectionById(id);
    }
}
