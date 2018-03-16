package com.remswork.project.alice.web.controller;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.ScheduleServiceImpl;
import com.remswork.project.alice.web.service.exception.ClassServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyScheduleController {

	@Autowired
	private ScheduleServiceImpl scheduleService;
	@Autowired
	private ClassServiceImpl classService;

}
