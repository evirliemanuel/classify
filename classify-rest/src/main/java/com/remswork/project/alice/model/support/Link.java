package com.remswork.project.alice.model.support;

public class Link {
	
	private String link;
	private String rel;
	
	public Link() {
		super();
	}
	
	public Link(String link, String rel) {
		this();
		this.link = link;
		this.rel = rel;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel = rel;
	}
}
