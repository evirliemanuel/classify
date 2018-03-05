package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblformula")
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(name = "total_percentage")
    private int totalPercentage;
	
	@Column(name = "activity_percentage")
    private int activityPercentage;
	
	@Column(name = "assignment_percentage")
    private int assignmentPercentage;
	
	@Column(name = "attendance_percentage")
    private int attendancePercentage;
	
	@Column(name = "exam_percentage")
    private int examPercentage;
	
	@Column(name = "project_percentage")
    private int projectPercentage;
	
	@Column(name = "quiz_percentage")
    private int quizPercentage;
	
	@Column(name = "recitation_percentage")
    private int recitationPercentage;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;
    @Transient
    private List<Link> links;

    public Formula() {
        links = new ArrayList<>();
    }

    public Formula(int totalPercentage, int activityPercentage, int assignmentPercentage, int attendancePercentage,
                   int examPercentage, int projectPercentage, int quizPercentage,
                   int recitationPercentage) {
        this();
        this.totalPercentage = totalPercentage;
        this.activityPercentage = activityPercentage;
        this.assignmentPercentage = assignmentPercentage;
        this.attendancePercentage = attendancePercentage;
        this.examPercentage = examPercentage;
        this.projectPercentage = projectPercentage;
        this.quizPercentage = quizPercentage;
        this.recitationPercentage = recitationPercentage;
    }

    public Formula(long id, int totalPercentage, int activityPercentage, int assignmentPercentage,
                   int attendancePercentage, int examPercentage, int projectPercentage, int quizPercentage,
                   int recitationPercentage) {
        this(totalPercentage, activityPercentage, assignmentPercentage, attendancePercentage, examPercentage,
                projectPercentage, quizPercentage, recitationPercentage);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(int totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public int getActivityPercentage() {
        return activityPercentage;
    }

    public void setActivityPercentage(int activityPercentage) {
        this.activityPercentage = activityPercentage;
    }

    public int getAssignmentPercentage() {
        return assignmentPercentage;
    }

    public void setAssignmentPercentage(int assignmentPercentage) {
        this.assignmentPercentage = assignmentPercentage;
    }

    public int getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(int attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public int getExamPercentage() {
        return examPercentage;
    }

    public void setExamPercentage(int examPercentage) {
        this.examPercentage = examPercentage;
    }

    public int getProjectPercentage() {
        return projectPercentage;
    }

    public void setProjectPercentage(int projectPercentage) {
        this.projectPercentage = projectPercentage;
    }

    public int getQuizPercentage() {
        return quizPercentage;
    }

    public void setQuizPercentage(int quizPercentage) {
        this.quizPercentage = quizPercentage;
    }

    public int getRecitationPercentage() {
        return recitationPercentage;
    }

    public void setRecitationPercentage(int recitationPercentage) {
        this.recitationPercentage = recitationPercentage;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
            if(eachLink.getRel().equalsIgnoreCase(link.getRel())) {
                isExist = true;
                break;
            }
        }
        if(!isExist)
            links.add(link);
    }
}
