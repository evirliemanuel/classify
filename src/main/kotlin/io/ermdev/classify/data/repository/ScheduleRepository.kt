package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScheduleRepository : JpaRepository<Schedule, Long> {

    @Query("from Schedule where day=:day")
    fun findByDay(@Param("day") day: String): List<Schedule>

    @Query("from Lesson as a join a.schedules as b where b.id=:scheduleId")
    fun findLesson(@Param("scheduleId") scheduleId: Long): Lesson?
}