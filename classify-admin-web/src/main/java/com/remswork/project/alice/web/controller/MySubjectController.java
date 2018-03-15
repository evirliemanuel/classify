package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.web.service.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MySubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @GetMapping("subjects")
    public String showTeachers(ModelMap modelMap) {
        try {
            List<Subject> subject = subjectService.getSubjectList();
            if (subject != null) {
                modelMap.put("subjectList", subject);
            } else {
                modelMap.put("subjectList", new ArrayList<Subject>());
            }
        } catch (Exception e) {
            modelMap.put("subjectList", new ArrayList<Subject>());
        }
        return "subjects";
    }
}
