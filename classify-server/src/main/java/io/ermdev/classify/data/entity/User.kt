package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
           var username: String,
           var password: String)
