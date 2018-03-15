package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.web.bean.XcellHelperBean;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("class")
public class ClassController {

    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private SectionServiceImpl sectionService;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private ScheduleServiceImpl scheduleService;

    private com.remswork.project.alice.model.Class _class;

    @Autowired
    private XcellHelperBean xcellHelperBean;

    @GetMapping("students")
    public String showStudents(@RequestParam("classId") long classId, ModelMap modelMap) {
        try {
            List<Student> students = new ArrayList<>();
            classService.getStudentList(classId).parallelStream().forEach(student -> {
                students.add(student);
            });
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(final Student object1, final Student object2) {
                    return object1.getLastName().compareTo(object2.getLastName());
                }
            });
            modelMap.addAttribute("studentList", students);
        } catch (Exception e) {
            modelMap.addAttribute("studentList", new ArrayList<Student>());
        }
        return "student-list";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addClass(@RequestParam("teacherId") long teacherId,
                           @RequestParam("subjectId") long subjectId,
                           @RequestParam("sectionId") long sectionId,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("scheduleId") long[] scheduleIdList, ModelMap modelMap) {
        try {
            _class = new com.remswork.project.alice.model.Class();
            _class = classService.addClass(_class, teacherId, subjectId, sectionId);
            if (!file.isEmpty()) {
                for (Student student : xcellHelperBean.loadFile(xcellHelperBean.convert(file), true)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                boolean isExist = false;
                                for (Student s : studentService.getStudentList()) {
                                    if (s.getStudentNumber() == student.getStudentNumber()) {
                                        classService.addStudentById(_class.getId(), s.getId());
                                        isExist = true;
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    Student _student = studentService.addStudent(student, 112017101);
                                    classService.addStudentById(_class.getId(), _student.getId());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
            for (long id : scheduleIdList) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            classService.addScheduleById(_class.getId(), id);
                        } catch (ClassException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

            }
            return "redirect:/teacher/view?id=" + teacherId;
        } catch (Exception e) {
            return "redirect:/teacher/view?error=true&id=" + teacherId;
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteClassById(@RequestParam("teacherId") long teacherId,
                                  @RequestParam("classId") long classId, ModelMap modelMap) {
        List<com.remswork.project.alice.model.Class> classList = new ArrayList<>();
        try {
            classService.deleteClassById(classId);
            classList = classService.getClassListByTeacherId(teacherId);
        } catch (ClassException e) {
            e.printStackTrace();
        }

        modelMap.put("cclassList", classList);
        return "fragment/class-table";
    }

    @RequestMapping(value = "dox", method = RequestMethod.GET)
    public String doX() {
        return "test";
    }
}
