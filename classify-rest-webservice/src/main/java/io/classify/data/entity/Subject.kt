package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_subject")
class Subject(@Id
              @GeneratedValue(strategy = GenerationType.AUTO)
              var id: Long,

              var name: String,

              @OneToMany(mappedBy = "subject")
              var lessons: List<Lesson> = ArrayList()) {
}