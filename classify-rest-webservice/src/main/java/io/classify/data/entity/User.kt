package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id
           @GeneratedValue(strategy = GenerationType.AUTO)
           var id: Long = 0,

           var username: String = "",

           var password: String = "",

           @OneToOne(cascade = [(CascadeType.ALL)], mappedBy = "user")
           var teacher: Teacher? = null,

           @OneToOne(cascade = [(CascadeType.ALL)], mappedBy = "user")
           var student: Student? = null)
