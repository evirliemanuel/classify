package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblattendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String date;
	
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class _class;
	
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @Transient
    private List<Link> links;

    public Attendance() {
        links = new ArrayList<>();
    }

    public Attendance(String title, String date) {
        this();
        this.title = title;
        this.date = date;
    }

    public Attendance(long id, String title, String date) {
        this(title, date);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
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
            if (eachLink.getRel().equalsIgnoreCase(link.getRel())) {
                isExist = true;
                break;
            }
        }
        if (!isExist)
            links.add(link);
    }
}
