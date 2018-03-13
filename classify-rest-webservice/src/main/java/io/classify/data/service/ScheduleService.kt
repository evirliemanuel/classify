package io.classify.data.service

import io.classify.data.entity.Schedule
import io.classify.data.repository.ScheduleRepository
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScheduleService(@Autowired val scheduleRepository: ScheduleRepository) {

    fun findById(id: Long): Schedule {
        val schedule: Schedule? = scheduleRepository.findById(id)
        return schedule ?: throw EntityException("No schedule found")
    }
}