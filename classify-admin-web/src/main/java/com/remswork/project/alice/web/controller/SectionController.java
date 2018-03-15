package com.remswork.project.alice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.service.SectionService;
import com.remswork.project.alice.web.service.DepartmentServiceImpl;

@Controller
@RequestMapping("section")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;
	@Autowired
	private DepartmentServiceImpl departmentService;
	
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public String getSection(
			@RequestParam("query") String query, ModelMap modelMap) {
		try {
			List<Department> departmentList = departmentService.getDepartmentList();
			List<Section> sectionList = new ArrayList<>();
			Section section = null;

			if(!query.trim().isEmpty()) {
				try {
					long id = Long.parseLong(query.trim());
					section = sectionService.getSectionById(id);
				}catch(NumberFormatException e) {
					e.printStackTrace();
					section = null;
				}catch(SectionException e) {
					e.printStackTrace();
					section = null;
				}
				
				if(section != null)
					sectionList.add(section);
				else {
					for(Section t : sectionService.getSectionList()) {
						if(t.getName().equals(query.trim())) {
							sectionList.add(t);
							continue;
						}
					}
				}
			}
			if(sectionList.size() < 1) {
				sectionList = sectionService.getSectionList();
				modelMap.put("responseMessage", "No result found.");
			}else {
				modelMap.put("responseMessage", sectionList.size() + " Result found.");
			}
			modelMap.put("sectionList", sectionList);
			modelMap.put("departmentList", departmentList);
			return "section-table";
		} catch (SectionException | DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getSectionList(ModelMap modelMap) {
		try {
			List<Section> sectionList = sectionService.getSectionList();
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("sectionList", sectionList);
			modelMap.put("departmentList", departmentList);
			return "section";
		} catch (SectionException | DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addSection(
			@RequestParam("name") String name,
			@RequestParam("departmentId") long departmentId, ModelMap modelMap) {
		try {
			Section section = new Section(name);
			sectionService.addSection(section, departmentId);
			List<Section> sectionList = sectionService.getSectionList();
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("sectionList", sectionList);
			modelMap.put("departmentList", departmentList);
			return "section";
		} catch (SectionException | DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSectionById(
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("departmentId") long departmentId, ModelMap modelMap) {
		try {		
			Section newSection = new Section(name);
			try {
				sectionService.updateSectionById(id, newSection, departmentId);
			}catch(SectionException e) {
				try {
					e.printStackTrace();
					departmentId = 0;
					sectionService.updateSectionById(id, newSection, departmentId);
				}catch(SectionException e2) {
					e2.printStackTrace();
				}
			}
			List<Section> sectionList = sectionService.getSectionList();
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("sectionList", sectionList);
			modelMap.put("departmentList", departmentList);
			return "section";
		} catch (SectionException | DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteSectionById(@RequestParam("id") long id, ModelMap modelMap) {
		try {
			sectionService.deleteSectionById(id);
			List<Section> sectionList = sectionService.getSectionList();
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("sectionList", sectionList);
			modelMap.put("departmentList", departmentList);
			modelMap.put("responseMessage", "You've successfully delete section with id : " + id);
			return "section-table";
		} catch (SectionException | DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
