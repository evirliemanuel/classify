package io.classify.data.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tbl_schedule")
class Schedule(@Id
               @GeneratedValue
               val id: Long = 0,
               val date: Date = Date(),
               val room: String = "",
               @ManyToOne(cascade = [CascadeType.ALL])
               @JoinColumn(name = "lesson_id")
               val lesson: Lesson = Lesson())