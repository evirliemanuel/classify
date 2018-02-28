package io.ermdev.alice.entity;

import mapfierj.NoRepeat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoRepeat
@Table(name="tblterm")
@Entity
public class Term {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private Integer semester;
    private Integer year;
    @ManyToOne
    private Curriculum curriculum;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tblterm_subject", joinColumns = @JoinColumn(name = "termId"),
            inverseJoinColumns = @JoinColumn(name="subjectId"))
    private List<Subject> subjects = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
