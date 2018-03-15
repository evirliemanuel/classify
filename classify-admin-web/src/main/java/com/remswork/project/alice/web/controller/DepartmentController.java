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
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.web.service.DepartmentServiceImpl;

@Controller
@RequestMapping("department")
public class DepartmentController {
	
	@Autowired
	private DepartmentServiceImpl departmentService;
	
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public String getTeacher(
			@RequestParam("query") String query, ModelMap modelMap) {
		try {
			List<Department> departmentList = new ArrayList<>();
			Department department = null;

			if(!query.trim().isEmpty()) {
				try {
					long id = Long.parseLong(query.trim());
					department = departmentService.getDepartmentById(id);
				}catch(NumberFormatException e) {
					e.printStackTrace();
					department = null;
				}catch(DepartmentException e) {
					e.printStackTrace();
					department = null;
				}
				
				if(department != null)
					departmentList.add(department);
				else {
					for(Department d : departmentService.getDepartmentList()) {
						if(d.getName().equals(query.trim())) {
							departmentList.add(d);
							continue;
						}
						if(d.getDescription().equals(query.trim())) {
							departmentList.add(d);
							continue;
						}
					}
				}
			}
			if(departmentList.size() < 1) {
				departmentList = departmentService.getDepartmentList();
				modelMap.put("responseMessage", "No result found.");
			}else {
				modelMap.put("responseMessage", departmentList.size() + " Result found.");
			}
			modelMap.put("departmentList", departmentList);
			return "department-table";
		} catch (DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDepartmentList(ModelMap modelMap) {
		try {
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("departmentList", departmentList);
			return "department";
		} catch (DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addDepartment(
			@RequestParam("name") String name,
			@RequestParam("description") String description, ModelMap modelMap) {
		try {
			Department department = new Department(name, description);
			departmentService.addDepartment(department);
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("departmentList", departmentList);
			return "department";
		} catch (DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateDepartmentById(
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("description") String description, ModelMap modelMap) {
		try {		
			Department newDepartment = new Department(name, description);
			try {
				departmentService.updateDepartmentById(id, newDepartment);
			}catch(DepartmentException e) {
				e.printStackTrace();
			}
			
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("departmentList", departmentList);
			return "department";
		} catch (DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteTeacherById(@RequestParam("id") long id, ModelMap modelMap) {
		try {
			departmentService.deleteDepartmentById(id);
			List<Department> departmentList = departmentService.getDepartmentList();
			modelMap.put("departmentList", departmentList);
			modelMap.put("responseMessage", "You've successfully delete department with id : " + id);
			return "department-table";
		} catch (DepartmentException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
