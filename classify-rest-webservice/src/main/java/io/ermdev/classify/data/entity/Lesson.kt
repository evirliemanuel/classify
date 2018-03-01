package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_lesson")
class Lesson(@Id
             @GeneratedValue(strategy = GenerationType.AUTO)
             var id: Long = 0,

             @ManyToOne(cascade = [CascadeType.ALL])
             @JoinColumn(name = "subject_id")
             var subject: Subject? = null,

             @ManyToOne(cascade = [CascadeType.ALL])
             @JoinColumn(name = "teacher_id")
             var teacher: Teacher? = null)