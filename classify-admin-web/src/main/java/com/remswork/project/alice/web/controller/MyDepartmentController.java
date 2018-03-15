package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.web.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyDepartmentController {
	
	@Autowired
	private DepartmentServiceImpl departmentService;

	@GetMapping("departments")
	public String showTeachers(ModelMap modelMap) {
		try {
			List<Department> departments = departmentService.getDepartmentList();
			if (departments != null) {
				modelMap.put("departmentList", departments);
			} else {
				modelMap.put("departmentList", new ArrayList<Department>());
			}
		} catch (Exception e) {
			modelMap.put("departmentList", new ArrayList<Department>());
		}
		return "departments";
	}
}
