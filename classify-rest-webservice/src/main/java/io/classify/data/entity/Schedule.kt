package io.classify.data.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tbl_schedule")
class Schedule(@Id
               @GeneratedValue
               var id: Long = 0,
               var date: Date = Date(),
               var room: String = "",
               @ManyToOne(cascade = [CascadeType.ALL])
               @JoinColumn(name = "lesson_id")
               var lesson: Lesson = Lesson())