package io.ermdev.alice.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TermDto {

    private Long id;
    private Integer semester;
    private Integer year;
    private CurriculumDto curriculum;

    private List<SubjectDto> subjects = new ArrayList<>();

    public TermDto() {}

    public TermDto(Long id, Integer semester, Integer year) {
        this.id = id;
        this.semester = semester;
        this.year = year;
    }

    public TermDto(Long id, Integer semester, Integer year, CurriculumDto curriculum) {
        this.id = id;
        this.semester = semester;
        this.year = year;
        this.curriculum = curriculum;
    }

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

    public CurriculumDto getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(CurriculumDto curriculum) {
        this.curriculum = curriculum;
    }

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDto> subjects) {
        this.subjects = subjects;
    }
}
