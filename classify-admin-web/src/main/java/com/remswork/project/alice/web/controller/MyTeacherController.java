package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyTeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private SectionServiceImpl sectionService;

    @GetMapping("teachers")
    public String showTeachers(ModelMap modelMap) {
        try {
            List<Teacher> teachers = teacherService.getTeacherList();
            if (teachers != null) {
                modelMap.put("teacherList", teachers);
            } else {
                modelMap.put("teacherList", new ArrayList<Teacher>());
            }
        } catch (Exception e) {
            modelMap.put("teacherList", new ArrayList<Teacher>());
        }
        return "teachers";
    }
}
