package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long,
           var username: String,
           var password: String) {

    constructor() : this(0, "", "")

    override fun toString(): String {
        return "Id : ${id}, Username : ${username}, Password : ${password}";
    }
}
