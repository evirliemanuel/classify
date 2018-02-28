package io.ermdev.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_teacher")
class Teacher(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        var name: String = "",

        var email: String = "",

        @OneToOne
        @JoinColumn(name = "user_id")
        var user: User? = null)