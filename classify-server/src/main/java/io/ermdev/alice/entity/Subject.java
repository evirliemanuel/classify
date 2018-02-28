package io.ermdev.alice.entity;

import mapfierj.NoRepeat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoRepeat
@Table(name="tblsubject")
@Entity
public class Subject {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer unit;
    @ManyToMany(mappedBy = "subjects")
    private List<Term> terms = new ArrayList<>();
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Class> classes = new ArrayList<>();

    public Subject() {}

    public Subject(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
