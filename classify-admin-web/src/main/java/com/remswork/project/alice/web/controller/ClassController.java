package com.remswork.project.alice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.web.bean.XcellHelperBean;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.ScheduleServiceImpl;
import com.remswork.project.alice.web.service.SectionServiceImpl;
import com.remswork.project.alice.web.service.StudentServiceImpl;
import com.remswork.project.alice.web.service.SubjectServiceImpl;
import com.remswork.project.alice.web.service.TeacherServiceImpl;

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
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addClass(@RequestParam("teacherId") long teacherId,
			@RequestParam("subjectId") long subjectId,
			@RequestParam("sectionId") long sectionId,
			@RequestParam("file") MultipartFile file,
			@RequestParam("scheduleId") long[] scheduleIdList, ModelMap modelMap) {
		try {
			_class = new com.remswork.project.alice.model.Class();
			_class = classService.addClass(_class, teacherId, subjectId, sectionId);
			if(!file.isEmpty()) {
				for(Student student : xcellHelperBean.loadFile(xcellHelperBean.convert(file), true)) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								boolean isExist = false;
								for(Student s : studentService.getStudentList()) {
									if(s.getStudentNumber() == student.getStudentNumber()) {
										classService.addStudentById(_class.getId(), s.getId());
										isExist = true;
										break;
									}
								}
								if(!isExist) {
									Student _student = studentService.addStudent(student, 112017101);	
									classService.addStudentById(_class.getId(), _student.getId());
								}
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
			for(long id : scheduleIdList) {
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
			return "redirect:/teacher/view?id=" + _class.getTeacher().getId() ;
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
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
	
	@RequestMapping(value="dox",method=RequestMethod.GET)
	public String doX() {
		return "test";
	}
}
