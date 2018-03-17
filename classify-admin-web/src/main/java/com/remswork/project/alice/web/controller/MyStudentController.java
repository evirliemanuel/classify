package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.web.bean.XcellHelperBean;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.SectionServiceImpl;
import com.remswork.project.alice.web.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class MyStudentController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private SectionServiceImpl sectionService;
    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private XcellHelperBean xcellHelperBean;

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

    @PostMapping("student-import")
    public String importStudents(@RequestParam("classId") Long classId,
                                 @RequestParam("file") MultipartFile file,
                                 ModelMap modelMap) {
        try {
            final Set<Student> students = classService.getStudentList(classId);
            if (!file.isEmpty()) {
                for (Student student : xcellHelperBean.loadFile(xcellHelperBean.convert(file), true)) {
                    try {
                        Student s;
                        try {
                            s = studentService.getStudentById(student.getId());
                        } catch (Exception e) {
                            s = studentService.addStudent(student, 112017101);
                        }
                        classService.addStudentById(classId, s.getId());
                        students.add(student);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (students != null) {
                modelMap.put("studentList", students);
                System.out.println("tae");
            } else {
                //modelMap.put("studentList", new ArrayList<Student>());
            }
        } catch (Exception e) {
            //modelMap.put("studentList", new ArrayList<Student>());
        }
        return "student-list";
    }
}
