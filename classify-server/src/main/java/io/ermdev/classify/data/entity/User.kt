package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User(@Id
           @GeneratedValue(strategy = GenerationType.AUTO)
           var id: Long = 0,
           var username: String = "",
           var password: String = "",
           @OneToOne(mappedBy = "userId")
           var teacher: Teacher? = null) {

    override fun toString(): String {
        return "Id : ${id}, Username : ${username}, Password : ${password}";
    }
}
