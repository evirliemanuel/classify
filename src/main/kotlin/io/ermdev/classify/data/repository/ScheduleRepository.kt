package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScheduleRepository : JpaRepository<Schedule, Long> {

    @Query("SELECT a FROM Schedule as a WHERE a.day=:day")
    fun findByDay(@Param("day") day: String): List<Schedule>
}