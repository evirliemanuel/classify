package io.ermdev.alice.entity;

import javax.persistence.*;
import java.sql.Time;

@Table(name = "tblschedule")
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String day;
    private Time startTime;
    private Time endTime;
    private Double hours;
    private String room;

    @JoinColumn(name = "classId")
    @ManyToOne
    private Class _class ;

    public Schedule(){}


}
