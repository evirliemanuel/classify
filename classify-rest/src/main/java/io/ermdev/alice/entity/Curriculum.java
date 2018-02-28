package io.ermdev.alice.entity;

import io.ermdev.alice.dto.TermDto;
import mapfierj.MapTo;
import mapfierj.NoRepeat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoRepeat
@Table(name="tblcurriculum")
@Entity
public class Curriculum {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @MapTo(value = TermDto.class, strategy = MapTo.Strategy.COLLECTION)
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Term> terms = new ArrayList<>();

}
