package io.classify.data.repository

import io.classify.data.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScheduleRepository : JpaRepository<Schedule, Long> {

    @Query("from Schedule where id=:scheduleId")
    fun findById(@Param("scheduleId") scheduleId: Long): Schedule
}