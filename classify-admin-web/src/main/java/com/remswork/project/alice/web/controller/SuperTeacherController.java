package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SuperTeacherController {


    private TeacherServiceImpl teacherService;

    private DepartmentServiceImpl departmentService;

    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private SectionServiceImpl sectionService;

    @Autowired
    public SuperTeacherController(TeacherServiceImpl teacherService, DepartmentServiceImpl departmentService) {
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    @GetMapping("teachers")
    public String showTeachers(ModelMap modelMap) {
        try {
            final List<Teacher> teachers = teacherService.getTeacherList();
            if (teachers != null) {
                modelMap.put("teacherList", teachers);
            } else {
                throw new RuntimeException("No teacher found");
            }
        } catch (Exception e) {
            modelMap.put("teacherList", new ArrayList<Teacher>());
        }
        return "teachers";
    }

    @GetMapping("teacher-detail")
    public String showTeacherDetail(@RequestParam("teacherId") long teacherId, ModelMap modelMap) {
        try {
            final Teacher teacher = teacherService.getTeacherById(teacherId);
            final List<Class> classes = classService.getClassListByTeacherId(teacherId);
            if (teacher != null) {
                modelMap.put("teacher", teacher);
            } else {
                modelMap.put("teacher", null);
            }
            if (classes != null) {
                modelMap.addAttribute("classes", classes);
            } else {
                modelMap.put("classes", new ArrayList<Class>());
            }

        } catch (Exception e) {
            modelMap.put("teacher", null);
            modelMap.put("classes", new ArrayList<Class>());
        }
        return "teacher-detail";
    }

    @GetMapping("teacher-class")
    public String showTeacherClass(@RequestParam("teacherId") long teacherId, ModelMap modelMap) {
        try {
            final Teacher teacher = teacherService.getTeacherById(teacherId);
            final List<Subject> allSubject = subjectService.getSubjectList();
            final List<Section> sectionList = sectionService.getSectionList();
            if (teacher != null) {
                modelMap.put("teacher", teacher);
            } else {
                modelMap.put("teacher", null);
            }
            if (allSubject != null) {
                modelMap.put("allSubject", allSubject);
            } else {
                modelMap.put("allSubject", new ArrayList<Subject>());
            }
            if (sectionList != null) {
                modelMap.put("sectionList", sectionList);
            } else {
                modelMap.put("sectionList", new ArrayList<Section>());
            }
        } catch (Exception e) {
            modelMap.put("teacher", null);
            modelMap.put("allSubject", new ArrayList<Subject>());
            modelMap.put("sectionList", new ArrayList<Section>());
        }
        return "teacher-class";
    }
}
