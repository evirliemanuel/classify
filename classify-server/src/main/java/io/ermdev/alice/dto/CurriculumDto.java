package io.ermdev.alice.dto;

import io.ermdev.alice.entity.Term;
import mapfierj.Excluded;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class CurriculumDto {

    private Long id;

    @Excluded
    private List<Term> terms = new ArrayList<>();

    public CurriculumDto() {}

    public CurriculumDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
}
