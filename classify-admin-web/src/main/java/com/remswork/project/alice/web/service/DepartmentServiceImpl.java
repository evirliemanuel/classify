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

import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.DepartmentService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.DepartmentServiceException;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "department";

	@Override
	public Department getDepartmentById(long id) throws DepartmentException {
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
				return (Department) response.readEntity(Department.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new DepartmentServiceException(message.getMessage());
			}else
				throw new DepartmentServiceException("The request might invalid or server is down");
		}catch(DepartmentServiceException e) {
			throw new DepartmentException(e.getMessage());
		}
	}

	@Override
	public List<Department> getDepartmentList() throws DepartmentException {
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
				return (List<Department>) response.readEntity(new GenericType<List<Department>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new DepartmentServiceException(message.getMessage());
			}else
				throw new DepartmentServiceException("The request might invalid or server is down");
		}catch(DepartmentServiceException e) {
			throw new DepartmentException(e.getMessage());
		}
	}
	
	@Override
	public Department addDepartment(Department department) throws DepartmentException {
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
			Response response = builder.post(Entity.json(department));
			
			if(response.getStatus() == 201) {
				return (Department) response.readEntity(Department.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new DepartmentServiceException(message.getMessage());
			}else
				throw new DepartmentServiceException("The request might invalid or server is down");
		}catch(DepartmentServiceException e) {
			throw new DepartmentException(e.getMessage());
		}
	}

	@Override
	public Department updateDepartmentById(long id, Department newDepartment) throws DepartmentException {
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
			Response response = builder.put(Entity.json(newDepartment));
			
			if(response.getStatus() == 200) {
				return (Department) response.readEntity(Department.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new DepartmentServiceException(message.getMessage());
			}else
				throw new DepartmentServiceException("The request might invalid or server is down");
		}catch(DepartmentServiceException e) {
			throw new DepartmentException(e.getMessage());
		}
	}
	
	@Override
	public Department deleteDepartmentById(long id) throws DepartmentException {
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
				return (Department) response.readEntity(Department.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new DepartmentServiceException(message.getMessage());
			}else
				throw new DepartmentServiceException("The request might invalid or server is down");
		}catch(DepartmentServiceException e) {
			throw new DepartmentException(e.getMessage());
		}
	}
}
