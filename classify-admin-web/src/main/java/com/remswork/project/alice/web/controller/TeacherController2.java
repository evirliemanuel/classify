package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.*;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController2 {


    private TeacherServiceImpl teacherService;

    private DepartmentServiceImpl departmentService;

    private ClassServiceImpl classService;

    private SubjectServiceImpl subjectService;

    private SectionServiceImpl sectionService;

    @Autowired
    public TeacherController2(TeacherServiceImpl teacherService,
                              DepartmentServiceImpl departmentService,
                              ClassServiceImpl classService,
                              SubjectServiceImpl subjectService,
                              SectionServiceImpl sectionService) {
        this.teacherService = teacherService;
        this.departmentService = departmentService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.sectionService = sectionService;
    }

    @GetMapping("teachers")
    public String showTeachers(@RequestParam(value = "q", required = false) String q, ModelMap modelMap) {
        try {
            final List<Teacher> teachers = new ArrayList<>();
            if (!StringUtils.isEmpty(q)) {
                try {
                    teachers.add(teacherService.getTeacherById(Long.parseLong(q)));
                } catch (Exception e) {
                    teachers.addAll(teacherService.getTeacherList());
                }
            } else {
                teachers.addAll(teacherService.getTeacherList());
            }
            modelMap.put("teacherList", teachers);
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

    @GetMapping("teacher-input")
    public String showTeacherInput(@RequestParam(value = "teacherId", required = false) Long teacherId, ModelMap modelMap) {
        try {
            final List<Department> departments = departmentService.getDepartmentList();
            if (teacherId != null) {
                final Teacher teacher = teacherService.getTeacherById(teacherId);
                if (teacher != null) {
                    modelMap.put("teacher", teacher);
                } else {
                    modelMap.put("teacher", null);
                }
            } else {
                modelMap.put("teacher", null);
            }
            modelMap.addAttribute("departmentList", departments);
        } catch (Exception e) {
            modelMap.put("teacher", null);
            modelMap.put("classes", new ArrayList<Class>());
        }
        return "teacher-input";
    }

    @GetMapping("teacher-class")
    public String showTeacherClassInput(@RequestParam("teacherId") long teacherId, ModelMap modelMap) {
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

    @PostMapping("teacher-add")
    public String addTeacher(
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("departmentId") long departmentId, ModelMap modelMap) {
        try {
            Teacher teacher = new Teacher(firstName, middleName, lastName, email);
            teacher = teacherService.addTeacher(teacher, departmentId);
            modelMap.put("teacher", teacher);
            return "teacher-detail";
        } catch (Exception e) {
            return "teacher-detail";
        }
    }

    @PostMapping("teacher-update")
    public String updateTeacher(
            @RequestParam("id") long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("departmentId") long departmentId, ModelMap modelMap) {
        try {
            Teacher newTeacher = new Teacher(firstName, lastName, middleName, email);
            try {
                teacherService.updateTeacherById(id, newTeacher, departmentId);
            } catch (TeacherException e) {
                try {
                    departmentId = 0;
                    teacherService.updateTeacherById(id, newTeacher, departmentId);
                } catch (TeacherException e2) {
                    e2.printStackTrace();
                }
            }
            Teacher teacher = teacherService.getTeacherById(id);
            modelMap.put("teacher", teacher);
            return "teacher-detail";
        } catch (TeacherException e) {
            return "teacher-detail";
        }
    }
}
