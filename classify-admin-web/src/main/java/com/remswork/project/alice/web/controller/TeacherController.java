package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.*;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@Controller
@RequestMapping("teacher")
public class TeacherController {

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

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public String getTeacher(
            @RequestParam("query") String query, ModelMap modelMap) {
        try {
            List<Department> departmentList = departmentService.getDepartmentList();
            List<Teacher> teacherList = new ArrayList<>();
            Teacher teacher = null;

            if (!query.trim().isEmpty()) {
                try {
                    long id = Long.parseLong(query.trim());
                    teacher = teacherService.getTeacherById(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    teacher = null;
                } catch (TeacherException e) {
                    e.printStackTrace();
                    teacher = null;
                }

                if (teacher != null)
                    teacherList.add(teacher);
                else {
                    for (Teacher t : teacherService.getTeacherList()) {
                        if (t.getFirstName().equals(query.trim())) {
                            teacherList.add(t);
                            continue;
                        }
                        if (t.getMiddleName().equals(query.trim())) {
                            teacherList.add(t);
                            continue;
                        }
                        if (t.getLastName().equals(query.trim())) {
                            teacherList.add(t);
                            continue;
                        }
                        if (t.getEmail().equals(query.trim())) {
                            teacherList.add(t);
                            continue;
                        }
                    }
                }
            }
            if (teacherList.size() < 1) {
                teacherList = teacherService.getTeacherList();
                modelMap.put("responseMessage", "No result found.");
            } else {
                modelMap.put("responseMessage", teacherList.size() + " Result found.");
            }
            modelMap.put("teacherList", teacherList);
            modelMap.put("departmentList", departmentList);
            return "fragment/teacher-table";
        } catch (TeacherException | DepartmentException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String getTeacherAndView(@RequestParam("id") long id, ModelMap modelMap) {
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            List<com.remswork.project.alice.model.Class> classList =
                    classService.getClassListByTeacherId(id);
            List<Subject> subjectList = subjectService.getSubjectListByTeacherId(id);
            List<Subject> allSubject = subjectService.getSubjectList();
            List<Section> sectionList = sectionService.getSectionList();
            modelMap.put("teacher", teacher);
            modelMap.put("cclassList", classList);
            modelMap.put("subjectList", subjectList);
            modelMap.put("allSubject", allSubject);
            modelMap.put("sectionList", sectionList);
            return "teacher-view";
        } catch (TeacherException | ClassException |
                SubjectException | SectionException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getTeacherList(ModelMap modelMap) {
        try {
            List<Teacher> teacherList = teacherService.getTeacherList();
            List<Department> departmentList = departmentService.getDepartmentList();
            modelMap.put("teacherList", teacherList);
            modelMap.put("departmentList", departmentList);
            return "teacher";
        } catch (TeacherException | DepartmentException e) {
            e.printStackTrace();
            return "error";
        }

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addTeacher(
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("departmentId") long departmentId, ModelMap modelMap) {
        try {
            Teacher teacher = new Teacher(firstName, middleName, lastName, email);
            teacherService.addTeacher(teacher, departmentId);
            List<Teacher> teacherList = teacherService.getTeacherList();
            List<Department> departmentList = departmentService.getDepartmentList();
            modelMap.put("teacherList", teacherList);
            modelMap.put("departmentList", departmentList);
            return "teacher";
        } catch (TeacherException | DepartmentException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateTeacherById(
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
                    e.printStackTrace();
                    departmentId = 0;
                    teacherService.updateTeacherById(id, newTeacher, departmentId);
                } catch (TeacherException e2) {
                    e2.printStackTrace();
                }
            }
            List<Teacher> teacherList = teacherService.getTeacherList();
            List<Department> departmentList = departmentService.getDepartmentList();
            modelMap.put("teacherList", teacherList);
            modelMap.put("departmentList", departmentList);
            return "teacher";
        } catch (TeacherException | DepartmentException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteTeacherById(@RequestParam("id") long id, ModelMap modelMap) {
        try {
            teacherService.deleteTeacherById(id);
            List<Teacher> teacherList = teacherService.getTeacherList();
            List<Department> departmentList = departmentService.getDepartmentList();
            modelMap.put("teacherList", teacherList);
            modelMap.put("departmentList", departmentList);
            modelMap.put("responseMessage", "You've successfully delete teacher with id : " + id);
            return "teachers";
        } catch (Exception e) {
            return "teachers";
        }
    }

}
