package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name="tblteacher")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_detail_id")
	private UserDetail userDetail;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="department_id")
	private Department department;

	@Transient
	private List<Link> links;
	
	public Teacher() {
		super();
		links = new ArrayList<>();
	}
	
	public Teacher(String firstName, String middleName, String lastName, String email) {
		this();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
	}

	public Teacher(long id, String firstName, String lastName, String middleName, String email) {
		this(firstName, middleName, lastName, email);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link){
		boolean isExist = false;
		for (Link eachLink : links) {
			if(eachLink.getRel().equalsIgnoreCase(link.getRel())) {
				isExist = true;
				break;
			}
		}
		if(!isExist)
			links.add(link);
	}
}
