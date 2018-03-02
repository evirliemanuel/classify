package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_student")
class Student(@Id
              @GeneratedValue(strategy = GenerationType.AUTO)
              var id: Long = 0,

              var number: Long = 0,

              var name: String = "",

              @OneToOne(cascade = [(CascadeType.ALL)])
              @JoinColumn(name = "user_id", unique = true)
              var user: User = User(),

              @ManyToMany(mappedBy = "students")
              var lessons: List<Lesson> = ArrayList())