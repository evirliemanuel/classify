package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblattendanceresult")
public class AttendanceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int status;
	
    @OneToOne
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;
	
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Transient
    private List<Link> links;

    public AttendanceResult() {
        links = new ArrayList<>();
    }

    public AttendanceResult(int status) {
        this.status = status;
    }

    public AttendanceResult(long id, int status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
