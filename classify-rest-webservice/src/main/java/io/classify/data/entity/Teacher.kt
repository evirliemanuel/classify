package io.classify.data.entity

import javax.persistence.*

@Entity
@Table(name = "tbl_teacher")
class Teacher(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        var name: String = "",

        var email: String = "",

        @OneToOne(cascade = [(CascadeType.ALL)])
        @JoinColumn(name = "user_id", unique = true)
        var user: User = User(),

        @OneToMany(mappedBy = "teacher")
        var lessons: List<Lesson> = ArrayList())