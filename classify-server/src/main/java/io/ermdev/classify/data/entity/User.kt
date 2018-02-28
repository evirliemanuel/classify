package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id
           @GeneratedValue(strategy = GenerationType.AUTO)
           var id: Long = 0,

           var username: String = "",

           var password: String = "",

           @OneToOne(mappedBy = "user")
           var teacher: Teacher? = null)
