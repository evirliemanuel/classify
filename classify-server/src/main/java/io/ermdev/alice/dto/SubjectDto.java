package io.ermdev.alice.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SubjectDto {

    private Long id;
    private String name;
    private String description;
    private Integer unit;
    private List<TermDto> terms = new ArrayList<>();
    private List<ClassDto> classes = new ArrayList<>();

    public SubjectDto() {}

    public SubjectDto(Long id, String name, String description, Integer unit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
    }

    public SubjectDto(Long id, String name, String description, Integer unit, List<TermDto> terms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.terms = terms;
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

    public List<TermDto> getTerms() {
        return terms;
    }

    public void setTerms(List<TermDto> terms) {
        this.terms = terms;
    }

    public List<ClassDto> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassDto> classes) {
        this.classes = classes;
    }
}
