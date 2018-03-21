package io.ermdev.classify.data.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_teacher")
class Teacher(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        var name: String = "",

        var email: String = "",

        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
        @JoinColumn(name = "user_id", unique = true)
        var user: User = User(),

        @OneToMany(mappedBy = "teacher", cascade = [CascadeType.REMOVE])
        var lessons: Set<Lesson> = HashSet()): Serializable