package com.remswork.project.alice.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.web.service.ClassServiceImpl;
import com.remswork.project.alice.web.service.ScheduleServiceImpl;
import com.remswork.project.alice.web.service.exception.ClassServiceException;

@Component
@RequestMapping(value="schedule")
public class ScheduleController {

	@Autowired
	private ScheduleServiceImpl scheduleService;
	@Autowired
	private ClassServiceImpl classService;

	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addSchedule(@RequestParam("day") String day,
			@RequestParam("room") String room, 
			@RequestParam("time") String time,
			@RequestParam("period") String period,ModelMap modelMap) {
		try {
			
			System.out.println("day : " + day);
			System.out.println("room : " + room);
			System.out.println("time : " + time);
			System.out.println("period : " + period);
		
		Schedule schedule = new Schedule();
			schedule.setDay(day);
			schedule.setTime(time);
			schedule.setPeriod(period);
			schedule.setRoom(room);
			schedule = scheduleService.addSchedule(schedule);
			modelMap.put("schedule", schedule);
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
		return "schedule-add";
	}
	
	@RequestMapping(value="c/add", method=RequestMethod.POST)
	public String addScheduleOnClass(@RequestParam("day") String day,
			@RequestParam("room") String room, 
			@RequestParam("time") String time,
			@RequestParam("period") String period,
			@RequestParam("classId") long classId, 
			ModelMap modelMap) {
		try {
		Schedule schedule = new Schedule();
			schedule.setDay(day);
			schedule.setTime(time);
			schedule.setPeriod(period);
			schedule.setRoom(room);
			schedule = scheduleService.addSchedule(schedule);
			classService.addScheduleById(classId, schedule.getId());
			Set<Schedule> scheduleList = classService.getScheduleList(classId);
			modelMap.put("scheduleList", scheduleList);
		} catch (ScheduleException | ClassException e) {
			e.printStackTrace();
		}
		return "schedule";
	}
	
	@RequestMapping(value="c/update", method=RequestMethod.POST)
	public String updateScheduleOnClass(@RequestParam("day") String day,
			@RequestParam("room") String room, 
			@RequestParam("time") String time,
			@RequestParam("period") String period,
			@RequestParam("classId") long classId,
			@RequestParam("scheduleId") long scheduleId,
			ModelMap modelMap) {
		try {
		Schedule schedule = new Schedule();
			schedule.setDay(day);
			schedule.setTime(time);
			schedule.setPeriod(period);
			schedule.setRoom(room);
			schedule = scheduleService.updateScheduleById(scheduleId, schedule);
			Set<Schedule> scheduleList = classService.getScheduleList(classId);
			modelMap.put("scheduleList", scheduleList);
		} catch (ScheduleException | ClassException e) {
			e.printStackTrace();
		}
		return "schedule";
	}

	@RequestMapping
	public String getScheduleListByClassId(@RequestParam("classId") long classId, ModelMap modelMap){
		try {
			Set<Schedule> scheduleList = classService.getScheduleList(classId);
			modelMap.put("scheduleList", scheduleList);
			return "schedule";
		} catch (ClassException e) {
			
			e.printStackTrace();
			return "error";
		}
		
	}

	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String deleteScheduleByClassId(@RequestParam("id") long id,
			@RequestParam("classId") long classId, ModelMap modelMap) {
		Set<Schedule> scheduleList = new HashSet<>();
		try {
			classService.deleteScheduleById(classId, id);
			scheduleList = classService.getScheduleList(classId);
		}catch(ClassServiceException e) {
			e.printStackTrace();
		} catch (ClassException e) {
			e.printStackTrace();
		}
		if(scheduleList.size() < 1) {
			modelMap.put("responseMessage", "No result found.");
		}else {
			modelMap.put("responseMessage", "Successfully deleted.");
		}
		modelMap.put("scheduleList", scheduleList);
		return "fragment/schedule-table";
	}
	

}
