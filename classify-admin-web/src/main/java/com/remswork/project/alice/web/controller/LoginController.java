package com.remswork.project.alice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "mylogin";
	}
	
	@RequestMapping(value="login/success", method=RequestMethod.POST)
	public String loginSuccess(ModelMap modelMap) {
		try {
			return "redirect:/teachers";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
