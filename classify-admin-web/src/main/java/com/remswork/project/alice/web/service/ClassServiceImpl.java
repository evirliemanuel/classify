package com.remswork.project.alice.web.service;

import java.util.List;
import java.util.Set;

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

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.ClassServiceException;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "class";
	
	@Override
	public Class getClassById(long id) throws ClassException {
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
				return (Class) response.readEntity(Class.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public List<Class> getClassList() throws ClassException {
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
				return (List<Class>) response.readEntity(new GenericType<List<Class>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
	
	@Override
	public List<Class> getClassListByStudentId(long studentId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.queryParam("studentId", studentId).request().get();
			
			if(response.getStatus() == 200) {
				return (List<Class>) response.readEntity(new GenericType<List<Class>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public List<Class> getClassListBySubjectId(long subjectId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.queryParam("subjectId", subjectId).request().get();
			
			if(response.getStatus() == 200) {
				return (List<Class>) response.readEntity(new GenericType<List<Class>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public List<Class> getClassListByTeacherId(long teacherId) throws ClassException {
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
				return (List<Class>) response.readEntity(new GenericType<List<Class>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
	
	@Override
	public Schedule getScheduleById(long classId, long scheduleId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("schedule");
			uri.append("/");
			uri.append(scheduleId);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Set<Schedule> getScheduleList(long classId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("schedule");
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (Set<Schedule>) response.readEntity(new GenericType<Set<Schedule>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Student getStudentById(long classId, long studentId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("student");
			uri.append("/");
			uri.append(studentId);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Set<Student> getStudentList(long classId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("student");
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Response response = target.request().get();
			
			if(response.getStatus() == 200) {
				return (Set<Student>) response.readEntity(new GenericType<Set<Student>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
	
	@Override
	public Class addClass(Class _class, long teacherId, long subjectId, long sectionId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target
					.queryParam("teacherId", teacherId)
					.queryParam("subjectId", subjectId)
					.queryParam("sectionId", sectionId)
					.request(MediaType.APPLICATION_JSON);
			Response response = builder.post(Entity.xml(_class));
			if(response.getStatus() == 200 || response.getStatus() == 201) {
				return (Class) response.readEntity(Class.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Schedule addScheduleById(long classId, long scheduleId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("schedule");
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target
					.queryParam("scheduleId", scheduleId)
					.request(MediaType.APPLICATION_JSON);
			Response response = builder.post(Entity.xml(new Schedule()));
			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Student addStudentById(long classId, long studentId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("student");
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target
					.queryParam("studentId", studentId)
					.request(MediaType.APPLICATION_JSON);
			Response response = builder.post(Entity.xml(new Student()));
			if(response.getStatus() == 200) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
	
	@Override
	public Class updateClassById(long id, Class newClass, long teacherId, long subjectId, long sectionId) 
			throws ClassException {
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
			Builder builder = target
					.queryParam("teacherId", teacherId)
					.queryParam("subjectId", subjectId)
					.queryParam("sectionId", sectionId).request();
			builder.accept("application/json");
			Response response = builder.put(Entity.json(newClass));
			
			if(response.getStatus() == 200) {
				return (Class) response.readEntity(Class.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
	
	@Override
	public Class deleteClassById(long id) throws ClassException {
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
				return (Class) response.readEntity(Class.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Schedule deleteScheduleById(long classId, long scheduleId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("schedule");
			uri.append("/");
			uri.append(scheduleId);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.request();
			builder.accept("application/json");
			Response response = builder.delete();

			if(response.getStatus() == 200) {
				return (Schedule) response.readEntity(Schedule.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}

	@Override
	public Student deleteStudentById(long classId, long studentId) throws ClassException {
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(targetProperties.getDomain());
			uri.append("/");
			uri.append(targetProperties.getBaseUri());
			uri.append("/");
			uri.append(payload);
			uri.append("/");
			uri.append(classId);
			uri.append("/");
			uri.append("student");
			uri.append("/");
			uri.append(studentId);
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(uri.toString());
			Builder builder = target.request();
			builder.accept("application/json");
			Response response = builder.delete();

			if(response.getStatus() == 200) {
				return (Student) response.readEntity(Student.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new ClassServiceException(message.getMessage());
			}else
				throw new ClassServiceException("The request might invalid or server is down");
		}catch(ClassServiceException e) {
			throw new ClassException(e.getMessage());
		}
	}
}
