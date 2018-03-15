package com.remswork.project.alice.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.SectionServiceImpl;
import com.remswork.project.alice.web.service.StudentServiceImpl;

@Controller
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	private StudentServiceImpl studentService;
	@Autowired
	private SectionServiceImpl sectionService;
	@Autowired
	private ClassServiceImpl classService;
	
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public String getStudent(
			@RequestParam("query") String query, ModelMap modelMap) {
		try {
			List<Section> sectionList = sectionService.getSectionList();
			List<Student> studentList = new ArrayList<>();
			Student student = null;

			if(!query.trim().isEmpty()) {
				try {
					long id = Long.parseLong(query.trim());
					student = studentService.getStudentById(id);
				}catch(NumberFormatException e) {
					e.printStackTrace();
					student = null;
				}catch(StudentException e) {
					e.printStackTrace();
					student = null;
				}
				
				if(student != null)
					studentList.add(student);
				else {
					for(Student s : studentService.getStudentList()) {
						if(s.getFirstName().equals(query.trim())) {
							studentList.add(s);
							continue;
						}
						if(s.getMiddleName().equals(query.trim())) {
							studentList.add(s);
							continue;
						}
						if(s.getLastName().equals(query.trim())) {
							studentList.add(s);
							continue;
						}
						try {
							if(s.getStudentNumber() == Integer.parseInt(query)) {
								studentList.add(s);
								continue;
							}
						} catch(NumberFormatException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if(studentList.size() < 1) {
				studentList = studentService.getStudentList();
				modelMap.put("responseMessage", "No result found.");
			}else {
				modelMap.put("responseMessage", studentList.size() + " Result found.");
			}
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			return "student-table";
		} catch (StudentException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String getStudentAndView(@RequestParam("id") long id, ModelMap modelMap) {
		try {
			Student student = studentService.getStudentById(id);
			modelMap.put("student", student);
			return "student-view";
		} catch (StudentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStudentList(ModelMap modelMap) {
		try {
			List<Student> studentList = studentService.getStudentList();
			List<Section> sectionList = sectionService.getSectionList();
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			return "student";
		} catch (StudentException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value="clist", method = RequestMethod.GET)
	public String getStudentListByClassId(@RequestParam("classId") long classId, ModelMap modelMap) {
		try {
			Set<Student> studentList = classService.getStudentList(classId);
			List<Section> sectionList = sectionService.getSectionList();
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			return "student";
		} catch (ClassException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addStudent(
			@RequestParam("studentNumber") long studentNumber,
			@RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName,
			@RequestParam("lastName") String lastName,
			@RequestParam("gender") String gender,
			@RequestParam("age") int age,
			@RequestParam("sectionId") long sectionId, ModelMap modelMap) {
		try {
			Student student = new Student(studentNumber, firstName, lastName, middleName, gender, age, "");
			studentService.addStudent(student, sectionId);
			List<Student> studentList = studentService.getStudentList();
			List<Section> sectionList = sectionService.getSectionList();
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			return "student";
		} catch (StudentException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateStudentById(
			@RequestParam("id") long id,
			@RequestParam("studentNumber") long studentNumber,
			@RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName,
			@RequestParam("lastName") String lastName,
			@RequestParam("gender") String gender,
			@RequestParam("age") int age,
			@RequestParam("sectionId") long sectionId, ModelMap modelMap) {
		try {		
			Student newStudent = new Student(studentNumber, firstName, lastName, middleName, gender, age, "");
			try {
				studentService.updateStudentById(id, newStudent, sectionId);
			}catch(StudentException e) {
				try {
					e.printStackTrace();
					sectionId = 0;
					studentService.updateStudentById(id, newStudent, sectionId);
				}catch(StudentException e2) {
					e2.printStackTrace();
				}
			}
			List<Student> studentList = studentService.getStudentList();
			List<Section> sectionList = sectionService.getSectionList();
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			return "student";
		} catch (StudentException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteStudentById(@RequestParam("id") long id, ModelMap modelMap) {
		try {
			studentService.deleteStudentById(id);
			List<Student> studentList = studentService.getStudentList();
			List<Section> sectionList = sectionService.getSectionList();
			modelMap.put("studentList", studentList);
			modelMap.put("sectionList", sectionList);
			modelMap.put("responseMessage", "You've successfully delete student with id : " + id);
			return "student-table";
		} catch (StudentException | SectionException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
