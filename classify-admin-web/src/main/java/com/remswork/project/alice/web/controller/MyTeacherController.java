package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("teacher-detail")
    public String showTeacherDetail(@RequestParam("teacherId") long teacherId,  ModelMap modelMap) {
        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);
            List<Class> classes = classService.getClassListByTeacherId(teacherId);
            if (teacher != null) {
                modelMap.put("teacher", teacher);
            } else {
                modelMap.put("teacher", null);
            }
            if (classes == null) {
                classes = new ArrayList<>();
            }
            modelMap.addAttribute("classes", classes);
        } catch (Exception e) {
            modelMap.put("teacher", null);
        }
        return "teacher-detail";
    }

    @GetMapping("teacher-class")
    public String showTeacherClassInsert(@RequestParam("teacherId") long teacherId,  ModelMap modelMap) {
        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);
            List<Subject> allSubject = subjectService.getSubjectList();
            List<Section> sectionList = sectionService.getSectionList();
            modelMap.put("allSubject", allSubject);
            modelMap.put("sectionList", sectionList);
            if (teacher != null) {
                modelMap.put("teacher", teacher);
            } else {
                modelMap.put("teacher", null);
            }
        } catch (Exception e) {
            modelMap.put("teacher", null);
        }
        return "teacher-class";
    }
}
