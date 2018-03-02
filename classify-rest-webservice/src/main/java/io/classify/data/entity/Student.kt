package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_student")
class Student(@Id
              @GeneratedValue(strategy = GenerationType.AUTO)
              var id: Long,

              var name: String,

              @ManyToMany(mappedBy = "students")
              var lessons: List<Lesson> = ArrayList())