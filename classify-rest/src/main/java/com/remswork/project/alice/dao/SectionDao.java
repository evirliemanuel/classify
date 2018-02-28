package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Section;

import java.util.List;

public interface SectionDao {

    Section getSectionById(long id) throws SectionException;

    List<Section> getSectionList() throws SectionException;

    Section addSection(Section section, long departmentId) throws SectionException;

    Section updateSectionById(long id, Section newSection, long departmentId) throws SectionException;

    Section deleteSectionById(long id) throws SectionException;
}
