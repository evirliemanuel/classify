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

import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.UserDetailServiceException;

@Service
public class UserDetailServiceImpl implements UserDetailService {
	
	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "userDetail";

	@Override
	public UserDetail getUserDetailById(long id) throws UserDetailServiceException {
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
				return (UserDetail) response.readEntity(UserDetail.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new UserDetailServiceException(message.getMessage());
			}else
				throw new UserDetailServiceException("The request might invalid or server is down");
		}catch(UserDetailServiceException e) {
			throw new UserDetailServiceException(e.getMessage());
		}
	}

	@Override
	public List<UserDetail> getUserDetailList() throws UserDetailServiceException {
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
				return (List<UserDetail>) response.readEntity(new GenericType<List<UserDetail>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new UserDetailServiceException(message.getMessage());
			}else
				throw new UserDetailServiceException("The request might invalid or server is down");
		}catch(UserDetailServiceException e) {
			throw new UserDetailServiceException(e.getMessage());
		}
	}
	
	@Override
	@Deprecated
	public UserDetail addUserDetail(UserDetail userDetail) throws UserDetailServiceException {
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
			Response response = builder.post(Entity.json(userDetail));
			
			if(response.getStatus() == 201) {
				return (UserDetail) response.readEntity(UserDetail.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new UserDetailServiceException(message.getMessage());
			}else
				throw new UserDetailServiceException("The request might invalid or server is down");
		}catch(UserDetailServiceException e) {
			throw new UserDetailServiceException(e.getMessage());
		}
	}

	@Override
	public UserDetail updateUserDetailById(long id, UserDetail newUserDetail) throws UserDetailServiceException {
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
			Response response = builder.put(Entity.json(newUserDetail));
			
			if(response.getStatus() == 200) {
				return (UserDetail) response.readEntity(UserDetail.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new UserDetailServiceException(message.getMessage());
			}else
				throw new UserDetailServiceException("The request might invalid or server is down");
		}catch(UserDetailServiceException e) {
			throw new UserDetailServiceException(e.getMessage());
		}
	}
	
	@Override
	@Deprecated
	public UserDetail deleteUserDetailById(long id) throws UserDetailServiceException {
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
				return (UserDetail) response.readEntity(UserDetail.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new UserDetailServiceException(message.getMessage());
			}else
				throw new UserDetailServiceException("The request might invalid or server is down");
		}catch(UserDetailServiceException e) {
			throw new UserDetailServiceException(e.getMessage());
		}
	}
}
