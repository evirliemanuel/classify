package com.remswork.project.alice.web.service;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.TeacherService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.TeacherServiceException;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "teacher";
	
	@Override
	public Teacher getTeacherById(long id) throws TeacherException {
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
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}

	@Override
	public List<Teacher> getTeacherList() throws TeacherException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request(MediaType.APPLICATION_JSON).get();
			
			if(response.getStatus() == 200) {
				return (List<Teacher>) response.readEntity(new GenericType<List<Teacher>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}
	
	@Deprecated
	@Override
	public Teacher addTeacher(Teacher teacher) throws TeacherException {
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
			Response response = builder.post(Entity.json(teacher));
			
			if(response.getStatus() == 201) {
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}

	@Override
	public Teacher addTeacher(Teacher teacher, long departmentId) throws TeacherException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.queryParam("departmentId", departmentId).request();
			builder.accept("application/json");
			Response response = builder.post(Entity.json(teacher));
			
			if(response.getStatus() == 201) {
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}

	@Deprecated
	@Override
	public Teacher updateTeacherById(long id, Teacher newTeacher) throws TeacherException {
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
			Response response = builder.put(Entity.json(newTeacher));
			
			if(response.getStatus() == 200) {
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}

	@Override
	public Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) throws TeacherException {
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
			Builder builder = target.queryParam("departmentId", departmentId).request();
			builder.accept("application/json");
			Response response = builder.put(Entity.json(newTeacher));
			
			if(response.getStatus() == 200) {
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}
	
	@Override
	public Teacher deleteTeacherById(long id) throws TeacherException {
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
				return (Teacher) response.readEntity(Teacher.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new TeacherServiceException(message.getMessage());
			}else
				throw new TeacherServiceException("The request might invalid or server is down");
		}catch(TeacherServiceException e) {
			throw new TeacherException(e.getMessage());
		}
	}

}
