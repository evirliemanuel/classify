package io.ermdev.classify.data.entity

import java.sql.Time
import javax.persistence.*

@Entity
@Table(name = "tbl_schedule")
class Schedule(@Id
               @GeneratedValue(strategy = GenerationType.AUTO)
               var id: Long = 0,

               var day: String = "",

               var room: String = "",

               var start: Time = Time(System.currentTimeMillis()),

               var end: Time = Time(System.currentTimeMillis()),

               @ManyToOne(cascade = [CascadeType.PERSIST])
               @JoinColumn(name = "lessonId", nullable = false)
               var lesson: Lesson = Lesson()) {

    override fun toString(): String {
        return "start = $start,  end = $end"
    }
}