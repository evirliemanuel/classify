package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_lesson")
class Lesson(@Id
             @GeneratedValue(strategy = GenerationType.AUTO)
             var id: Long = 0,

             @ManyToOne(cascade = [CascadeType.ALL])
             @JoinColumn(name = "subject_id")
             var subject: Subject? = Subject(),

             @ManyToOne(cascade = [CascadeType.ALL])
             @JoinColumn(name = "teacher_id")
             var teacher: Teacher? = Teacher(),

             @ManyToMany(cascade = [CascadeType.ALL])
             @JoinTable(name = "tbl_student_lesson", joinColumns = [JoinColumn(name = "lesson_id")],
                     inverseJoinColumns = [(JoinColumn(name = "student_id"))])
             var students: MutableList<Student> = ArrayList())