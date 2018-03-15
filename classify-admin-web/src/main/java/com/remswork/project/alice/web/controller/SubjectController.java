package com.remswork.project.alice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.web.service.SubjectServiceImpl;

@Controller
@RequestMapping("subject")
public class SubjectController {
	
	@Autowired
	private SubjectServiceImpl subjectService;
	
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public String getTeacher(
			@RequestParam("query") String query, ModelMap modelMap) {
		try {
			List<Subject> subjectList = new ArrayList<>();
			Subject subject = null;

			if(!query.trim().isEmpty()) {
				try {
					long id = Long.parseLong(query.trim());
					subject = subjectService.getSubjectById(id);
				}catch(NumberFormatException e) {
					e.printStackTrace();
					subject = null;
				}catch(SubjectException e) {
					e.printStackTrace();
					subject = null;
				}
				
				if(subject != null)
					subjectList.add(subject);
				else {
					for(Subject d : subjectService.getSubjectList()) {
						if(d.getName().equals(query.trim())) {
							subjectList.add(d);
							continue;
						}
						if(d.getCode().equals(query.trim())) {
							subjectList.add(d);
							continue;
						}
						if(d.getDescription().equals(query.trim())) {
							subjectList.add(d);
							continue;
						}
					}
				}
			}
			if(subjectList.size() < 1) {
				subjectList = subjectService.getSubjectList();
				modelMap.put("responseMessage", "No result found.");
			}else {
				modelMap.put("responseMessage", subjectList.size() + " Result found.");
			}
			modelMap.put("subjectList", subjectList);
			return "subject-table";
		} catch (SubjectException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getSubjectList(ModelMap modelMap) {
		try {
			List<Subject> subjectList = subjectService.getSubjectList();
			modelMap.put("subjectList", subjectList);
			return "subject";
		} catch (SubjectException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addSubject(
			@RequestParam("name") String name,
			@RequestParam("code") String code,
			@RequestParam("description") String description,
			@RequestParam("unit") int unit, ModelMap modelMap) {
		try {
			Subject subject = new Subject(name, code, description, unit);
			subjectService.addSubject(subject);
			List<Subject> subjectList = subjectService.getSubjectList();
			modelMap.put("subjectList", subjectList);
			return "subject";
		} catch (SubjectException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubjectById(
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("code") String code,
			@RequestParam("description") String description,
			@RequestParam("unit") int unit, ModelMap modelMap) {
		try {		
			Subject newSubject = new Subject(name, code, description, unit);
			try {
				subjectService.updateSubjectById(id, newSubject);
			}catch(SubjectException e) {
				e.printStackTrace();
			}
			
			List<Subject> subjectList = subjectService.getSubjectList();
			modelMap.put("subjectList", subjectList);
			return "subject";
		} catch (SubjectException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteTeacherById(@RequestParam("id") long id, ModelMap modelMap) {
		try {
			subjectService.deleteSubjectById(id);
			List<Subject> subjectList = subjectService.getSubjectList();
			modelMap.put("subjectList", subjectList);
			modelMap.put("responseMessage", "You've successfully delete subject with id : " + id);
			return "subject-table";
		} catch (SubjectException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
