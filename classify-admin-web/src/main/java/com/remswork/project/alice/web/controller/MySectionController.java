package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.SectionService;
import com.remswork.project.alice.web.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MySectionController {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private DepartmentServiceImpl departmentService;

    @GetMapping("sections")
    public String showTeachers(ModelMap modelMap) {
        try {
            List<Section> sections = sectionService.getSectionList();
            if (sections != null) {
                modelMap.put("sectionList", sections);
            } else {
                modelMap.put("sectionList", new ArrayList<Subject>());
            }
        } catch (Exception e) {
            modelMap.put("sectionList", new ArrayList<Subject>());
        }
        return "sections";
    }
}
