package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name="tblstudent")
public class Student {

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;
	
	@Column(name = "student_number")
    private long studentNumber;
	
	@Column(name = "first_name")
    private String firstName;
	
	@Column(name = "last_name")
    private String lastName;
	
	@Column(name = "middle_name")
    private String middleName;

    private String gender;
    private int age;
	
	@Column(name = "image_src")
    private String imageSrc;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_detail_id")
    private UserDetail userDetail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private Section section;
    @Transient
    private List<Link> links;

    public Student(){
        super();
        links = new ArrayList<>();
    }

    public Student(long studentNumber, String firstName, String middleName, String lastName,
                   String gender, int age) {
        this();
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public Student(long studentNumber, String firstName, String middleName, String lastName,
                   String gender, int age, String imageSrc) {
        this(studentNumber, firstName, middleName, lastName, gender, age);
        this.imageSrc = imageSrc;
    }

    public Student(long id, long studentNumber, String firstName, String lastName, String middleName,
                   String gender, int age, String imageSrc) {
        this(studentNumber, firstName, lastName, middleName, gender, age, imageSrc);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
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
