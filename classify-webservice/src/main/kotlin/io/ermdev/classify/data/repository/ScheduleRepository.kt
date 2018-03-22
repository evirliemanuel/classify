package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Long> {

}