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

import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.SubjectService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.SubjectServiceException;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "subject";
	
	@Override
	public Subject getSubjectById(long id) throws SubjectException {
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
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}
	
	@Override
	public Subject getSubjectByClassAndTeacherId(long classId, long teacherId) 
			throws SubjectException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target
				.queryParam("classId", classId)
				.queryParam("teacherId", teacherId)
				.request().get();
			
			if(response.getStatus() == 200) {
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}

	@Override
	public Subject getSubjectByScheduleId(long scheduleId) throws SubjectException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target
				.queryParam("scheduleId", scheduleId)
				.request().get();
			
			if(response.getStatus() == 200) {
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}

	@Deprecated
	@Override
	public List<Subject> getSubjectList() throws SubjectException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request(MediaType.APPLICATION_XML).get();
			
			if(response.getStatus() == 200) {
				return (List<Subject>) response.readEntity(new GenericType<List<Subject>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}
	
	@Override
	public List<Subject> getSubjectListByStudentId(long studentId) throws SubjectException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target
					.queryParam("studentId", studentId)
					.request(MediaType.APPLICATION_XML).get();
			
			if(response.getStatus() == 200) {
				return (List<Subject>) response.readEntity(new GenericType<List<Subject>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}

	@Override
	public List<Subject> getSubjectListByTeacherId(long teacherId) throws SubjectException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/1");
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target
					.queryParam("teacherId", teacherId)
					.request(MediaType.APPLICATION_XML).get();
			
			if(response.getStatus() == 200) {
				return (List<Subject>) response.readEntity(new GenericType<List<Subject>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}

	@Override
	public Subject addSubject(Subject schedule) throws SubjectException {
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
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}

	@Override
	public Subject updateSubjectById(long id, Subject newSubject) throws SubjectException {
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
			Response response = builder.put(Entity.json(newSubject));
			
			if(response.getStatus() == 200) {
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}
	
	@Override
	public Subject deleteSubjectById(long id) throws SubjectException {
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
				return (Subject) response.readEntity(Subject.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SubjectServiceException(message.getMessage());
			}else
				throw new SubjectServiceException("The request might invalid or server is down");
		}catch(SubjectServiceException e) {
			throw new SubjectException(e.getMessage());
		}
	}
}
