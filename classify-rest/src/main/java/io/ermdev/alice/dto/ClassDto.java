package io.ermdev.alice.dto;

import io.ermdev.alice.entity.Schedule;
import io.ermdev.alice.entity.Teacher;
import mapfierj.Excluded;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ClassDto {

    private Long id;
    private StudentDto student;
    private SubjectDto subject;
    private Teacher teacher;
    @Excluded
    private List<Schedule> schedules = new ArrayList<>();

    public ClassDto() {}

    public ClassDto(Long id, StudentDto student) {
        this.id = id;
        this.student = student;
    }

    public ClassDto(Long id, StudentDto student, SubjectDto subject) {
        this.id = id;
        this.student = student;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }
}
