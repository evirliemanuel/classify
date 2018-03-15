package com.remswork.project.alice.web.service;

import java.util.List;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.ScheduleService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.ScheduleServiceException;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "schedule";
	
	@Override
	public Schedule getScheduleById(long id) throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(id);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}
	
	@Override
	public List<Schedule> getScheduleList() throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (List<Schedule>) response.readEntity(new GenericType<List<Schedule>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}
	
	@Override
	public Set<Schedule> getScheduleListByTeacherId(long teacherId) throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.queryParam("teacherId", teacherId).request().get();
			
			if(response.getStatus() == 200) {
				return (Set<Schedule>) response.readEntity(new GenericType<Set<Schedule>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}
	
	@Override
	public Schedule addSchedule(Schedule schedule) throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.request();
			builder.accept("application/json");
			Response response = builder.post(Entity.json(schedule));
			
			if(response.getStatus() == 201) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}

	@Override
	public Schedule updateScheduleById(long id, Schedule newSchedule) throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(id);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.request();
			builder.accept("application/json");
			Response response = builder.put(Entity.json(newSchedule));
			
			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}
	
	@Override
	public Schedule deleteScheduleById(long id) throws ScheduleException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(id);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.request();
			builder.accept("application/json");
			Response response = builder.delete();
			
			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ScheduleServiceException(message.getMessage());
			}else
				throw new ScheduleServiceException("The request might invalid or server is down");
		}catch(ScheduleServiceException e) {
			throw new ScheduleException(e.getMessage());
		}
	}
}
