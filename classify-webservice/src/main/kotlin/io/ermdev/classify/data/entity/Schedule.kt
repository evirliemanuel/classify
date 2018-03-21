package io.ermdev.classify.data.entity

import java.sql.Time
import javax.persistence.*

class Schedule(@Id
               @GeneratedValue(strategy = GenerationType.AUTO)
               val id: Long = 0,

               val day: String = "",

               val room: String = "",

               val start: Time = Time(System.currentTimeMillis()),

               val end: Time = Time(System.currentTimeMillis()),

               @ManyToOne(cascade = [CascadeType.PERSIST])
               @JoinColumn(name = "lessonId")
               val lesson: Lesson = Lesson())