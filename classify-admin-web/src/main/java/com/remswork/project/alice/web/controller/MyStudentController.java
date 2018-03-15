package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyStudentController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private SectionServiceImpl sectionService;
    @Autowired
    private ClassServiceImpl classService;

    @GetMapping("students")
    public String showStudents(ModelMap modelMap) {
        try {
            List<Student> students = studentService.getStudentList();
            if (students != null) {
                modelMap.put("studentList", students);
            } else {
                modelMap.put("studentList", new ArrayList<Student>());
            }
        } catch (Exception e) {
            modelMap.put("studentList", new ArrayList<Student>());
        }
        return "students";
    }
}
