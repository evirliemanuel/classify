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

import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.SectionService;
import com.remswork.project.alice.web.bean.TargetPropertiesBean;
import com.remswork.project.alice.web.service.exception.SectionServiceException;

@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private TargetPropertiesBean targetProperties;
	private final String payload = "section";
	
	@Override
	public Section getSectionById(long id) throws SectionException {
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
				return (Section) response.readEntity(Section.class);
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SectionServiceException(message.getMessage());
			}else
				throw new SectionServiceException("The request might invalid or server is down");
		}catch(SectionServiceException e) {
			throw new SectionException(e.getMessage());
		}
	}

	@Override
	public List<Section> getSectionList() throws SectionException {
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
				return (List<Section>) response.readEntity(new GenericType<List<Section>>() {});
			}else if(response.getStatus() == 404){
				Message message = (Message) response.readEntity(Message.class);
				throw new SectionServiceException(message.getMessage());
			}else
				throw new SectionServiceException("The request might invalid or server is down");
		}catch(SectionServiceException e) {
			throw new SectionException(e.getMessage());
		}
	}
	
	@Override
	public Section addSection(Section Section, long departmentId) throws SectionException {
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
			Response response = builder.post(Entity.json(Section));
			
			if(response.getStatus() == 201) {
				return (Section) response.readEntity(Section.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SectionServiceException(message.getMessage());
			}else
				throw new SectionServiceException("The request might invalid or server is down");
		}catch(SectionServiceException e) {
			throw new SectionException(e.getMessage());
		}
	}

	@Override
	public Section updateSectionById(long id, Section newSection, long departmentId) throws SectionException {
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
			Response response = builder.put(Entity.json(newSection));
			
			if(response.getStatus() == 200) {
				return (Section) response.readEntity(Section.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SectionServiceException(message.getMessage());
			}else
				throw new SectionServiceException("The request might invalid or server is down");
		}catch(SectionServiceException e) {
			throw new SectionException(e.getMessage());
		}
	}
	
	@Override
	public Section deleteSectionById(long id) throws SectionException {
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
				return (Section) response.readEntity(Section.class);
			}else if(response.getStatus() == 400){
				Message message = (Message) response.readEntity(Message.class);
				throw new SectionServiceException(message.getMessage());
			}else
				throw new SectionServiceException("The request might invalid or server is down");
		}catch(SectionServiceException e) {
			throw new SectionException(e.getMessage());
		}
	}
}
