package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblgrade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double totalScore;
    private double activityScore;
    private double assignmentScore;
    private double attendanceScore;
    private double examScore;
    private double projectScore;
    private double quizScore;
    @OneToOne
    @JoinColumn(name = "termId")
    private Term term;
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @OneToOne
    @JoinColumn(name = "classId")
    private Class _class;
    @Transient
    private List<Link> links;

    public Grade() {
        links = new ArrayList<>();
    }

    public Grade(double totalScore, double activityScore, double assignmentScore, double attendanceScore,
                 double examScore, double projectScore, double quizScore) {
        this();
        this.totalScore = totalScore;
        this.activityScore = activityScore;
        this.assignmentScore = assignmentScore;
        this.attendanceScore = attendanceScore;
        this.examScore = examScore;
        this.projectScore = projectScore;
        this.quizScore = quizScore;
    }

    public Grade(long id, double totalScore, double activityScore, double assignmentScore, double attendanceScore,
                 double examScore, double projectScore, double quizScore) {
        this();
        this.id = id;
        this.totalScore = totalScore;
        this.activityScore = activityScore;
        this.assignmentScore = assignmentScore;
        this.attendanceScore = attendanceScore;
        this.examScore = examScore;
        this.projectScore = projectScore;
        this.quizScore = quizScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(double activityScore) {
        this.activityScore = activityScore;
    }

    public double getAssignmentScore() {
        return assignmentScore;
    }

    public void setAssignmentScore(double assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(double attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public double getExamScore() {
        return examScore;
    }

    public void setExamScore(double examScore) {
        this.examScore = examScore;
    }

    public double getProjectScore() {
        return projectScore;
    }

    public void setProjectScore(double projectScore) {
        this.projectScore = projectScore;
    }

    public double getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(double quizScore) {
        this.quizScore = quizScore;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
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
