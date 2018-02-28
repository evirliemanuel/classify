package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblterm")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int code;
    @Transient
    private List<Link> links;

    public  Term() {
        links = new ArrayList<>();
    }

    public Term(String name, int code) {
        this();
        this.name = name;
        this.code = code;
    }

    public Term(long id, String name, int code) {
        this(name, code);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
