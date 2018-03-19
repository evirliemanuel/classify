package com.remswork.project.alice.web.service;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.StudentService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.StudentServiceException;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "student";
	
	@Override
	public Student getStudentById(long id) throws StudentException {
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
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}

	public Student getStudentBySn(long id) throws StudentException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("?sn=");
			uri.append(id);

			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();

			if(response.getStatus() == 200) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}

	@Override
	public List<Student> getStudentList() throws StudentException {
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
				return (List<Student>) response.readEntity(new GenericType<List<Student>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}
	
	@Override
	public Student addStudent(Student Student, long sectionId) throws StudentException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.queryParam("sectionId", sectionId).request();
			builder.accept("application/json");
			Response response = builder.post(Entity.json(Student));
			
			if(response.getStatus() == 201) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}

	@Override
	public Student updateStudentById(long id, Student newStudent, long sectionId) throws StudentException {
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
			Builder builder = target.queryParam("sectionId", sectionId).request();
			builder.accept("application/json");
			Response response = builder.put(Entity.json(newStudent));
			
			if(response.getStatus() == 200) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}
	
	@Override
	public Student deleteStudentById(long id) throws StudentException {
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
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new StudentServiceException(message.getMessage());
			}else
				throw new StudentServiceException("The request might invalid or server is down");
		}catch(StudentServiceException e) {
			throw new StudentException(e.getMessage());
		}
	}
}
