package com.remswork.project.alice.web.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:target.properties")
public class TargetPropertiesBean {
	
	@Value("${domain}")
	private String domain;
	@Value("${base.uri}")
	private String baseUri;
	
	public TargetPropertiesBean() {
		super();
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getBaseUri() {
		return baseUri;
	}
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
}
