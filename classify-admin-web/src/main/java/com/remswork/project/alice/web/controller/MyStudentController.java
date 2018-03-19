package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.web.bean.XcellHelperBean;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.SectionServiceImpl;
import com.remswork.project.alice.web.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("student-input")
    public String showStudentInput() {
        return "student-input";
    }

    @PostMapping("student-add")
    public String addStudent(
            @RequestParam("studentNumber") long studentNumber,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("gender") String gender,
            @RequestParam("age") int age,
            @RequestParam(value = "sectionId", required = false) Long sectionId, ModelMap modelMap) {
        try {
            Student student = new Student(studentNumber, firstName, lastName, middleName, gender, age, "");
            studentService.addStudent(student, 0);
            List<Student> studentList = studentService.getStudentList();
            modelMap.put("studentList", studentList);
            return "students";
        } catch (Exception e) {
            return "students";
        }
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
                        System.out.println("id :" + student.getId());
                        System.out.println("sn :" + student.getStudentNumber());
                        Student s;
                        try {
                            s = studentService.getStudentBySn(student.getStudentNumber());
                            s = classService.addStudentById(classId, s.getId());
                            students.add(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //s = studentService.addStudent(student, 112017101);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (students != null) {
                modelMap.put("studentList", students);
            } else {
                modelMap.put("studentList", new ArrayList<Student>());
            }
        } catch (Exception e) {
            modelMap.put("studentList", new ArrayList<Student>());
        }
        return "student-list";
    }
}
